package org.example;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.notifications.MFXNotificationCenterSystem;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import io.github.palexdev.materialfx.notifications.base.INotification;
import io.github.palexdev.materialfx.utils.RandomUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Callback;
import org.example.api.*;
import org.example.api.model.Chat;
import org.example.api.model.Error;
import org.example.api.model.Message;
import org.example.api.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ChatController {
    private MFXGenericDialog dialogContent;// MFX
    private MFXStageDialog dialog; // MFX
    private MFXGenericDialog dialogContentEditUser;
    private MFXStageDialog dialogEditUser;
    private final ChatAPIClient chatAPIClient = new ChatAPIClient(); // API
    private final MessageAPIClient messageAPIClient = new MessageAPIClient(); // API
    private final NotificationAPIClient notificationAPIClient = new  NotificationAPIClient();
    public static UserAPIClient userApiALL = new UserAPIClient(); // API
    private User userRegister = App.userRegister; // usuario registrado
    private ArrayList<User> arrayListAllUsers = App.arrayListAllUsers; // lista de todos lo usuarios
    private ArrayList<Chat> arrayAllChatUsers ;
    private ListView<User> listViewAllUser = new ListView<>();
    @FXML
    private ListView<User> listViewUser = new ListView<>();
    @FXML
    private ListView<Message> listViewMessage = new ListView<>();
    private MFXButton bt; // Boton para añadir chat
    @FXML
    private TextField textFieldMessage;
    @FXML
    private MFXProgressSpinner progressBar ;
    @FXML
    private ImageView imageUserRegister;
    @FXML
    private Label labelName;
    private User userChecked;

    public void initialize() throws IOException, InterruptedException {
        try {
            imageUserRegister.setImage(new Image(userRegister.getPhotourl()));
        }catch (Exception e){

        }
        labelName.setText(userRegister.getUsername());

        // Modelo de las celda de los listView
        editCellViewUsers(listViewAllUser);
        editCellViewUsers(listViewUser);
        editCellViewMessage(listViewMessage);
        bt = styleButton("Crear chat");
        // Carga de chats del usuario
        chargeAllChats();
        // Carga los usuarios con los que tengo un chat
        userchats();
        notification();

        listViewUser.getSelectionModel().select(userChecked);

    }
    public void notification(){
        notificationAPIClient.observeNewMessages(userRegister.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                List<Chat> chats = (List<Chat>)response;
                if(!chats.isEmpty() && userRegister != null ){

                    for (Chat c : chats) {
                        messageAPIClient.getMessagesFromChat(c.getId(), new APICallback() {
                            @Override
                            public void onSuccess(Object response) throws IOException {
                                ArrayList<Message> messages = new ArrayList<>((List<Message>) response);
                                messages.sort(((o1, o2) -> o1.getId().compareTo(o2.getId())));
                                if(messages.get(messages.size()-1).getIdsender() != userRegister.getId()){
                                    userchats();
                                    String mensaje = "Nuevo mensaje de ";
                                    if(c.getUser2_id() == userRegister.getId()){
                                        mensaje += c.getUser1_username();
                                    }else{
                                        mensaje += c.getUser2_username();
                                    }
                                    mensaje += "\n";
                                    showAlert("Mensaje",mensaje);

                                }
                            }
                            @Override
                            public void onError(Object error) {}});
                    }
                }
                chats.clear();
            }
            @Override
            public void onError(Object error) {
                System.out.println(((Error)error).getError());
            }
        });
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Conseguir los usuarios con los que tengo chats y conseguir el chat de un usuario seleccionado
     */
    public void userchats(){
        chatAPIClient.getAllChatsFromUser(userRegister.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                List<Chat> chatList = (List<Chat>) response;
                ArrayList<User> usersChats = new ArrayList<>();
                int id = userRegister.getId();

                for (Chat c: chatList) {
                    for (User u: arrayListAllUsers) {
                        if ((c.getUser2_id() == id && c.getUser1_id() == u.getId()) ||
                                (c.getUser1_id() == id &&  c.getUser2_id() == u.getId()) && !usersChats.contains(u)){
                            usersChats.add(0,u);
                            break;
                        }
                    }
                }
                ObservableList<User> chatUserRegister = FXCollections.observableArrayList(usersChats);
                listViewUser.setItems(chatUserRegister);
                progressBar.visibleProperty().setValue(false);
                listViewUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        for (Chat c: chatList) {
                            if((c.getUser2_id() == id && c.getUser1_id() == newValue.getId()) ||
                                    (c.getUser1_id() == id && c.getUser2_id() == newValue.getId())){
                                try {
                                    userChecked = newValue;
                                    seeMessage(c.getId());
                                    controllerMessage(c.getId());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                });

                deleteChat2();
            }
            @Override
            public void onError(Object error) {
                System.out.println(error);

            }
        });

    }

    public void seeMessage(int id_chat) throws IOException {
        messageAPIClient.getMessagesFromChat(id_chat, new APICallback() {
            @Override
            public void onSuccess(Object response) {
                ArrayList<Message> messages = new ArrayList<>((List<Message>) response);
                messages.sort(((o1, o2) -> o1.getId().compareTo(o2.getId())));
                ObservableList<Message> itemsMessage = FXCollections.observableArrayList(messages);
                listViewMessage.setItems(itemsMessage);
                listViewMessage.getSelectionModel().clearSelection();
            }

            @Override
            public void onError(Object error) {
                System.out.println(((Error) error).getError());
            }
        });
    }

    public void controllerMessage(int id_chat) throws IOException {
        textFieldMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        System.out.println("enter");
                        sendMessage(id_chat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendMessageLogo(){
        System.out.println("mensaje logo");
        chatAPIClient.getAllChatsFromUser(userRegister.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                List<Chat> chatList = (List<Chat>) response;
                User userSelect = listViewUser.getSelectionModel().getSelectedItem();

                for (Chat c : chatList){
                    if ((c.getUser2_id() == userRegister.getId() && c.getUser1_id() == userSelect.getId()) ||
                            (c.getUser1_id() == userRegister.getId() &&  c.getUser2_id() == userSelect.getId()) ){
                        sendMessage(c.getId());
                        break;
                    }
                }

            }
            @Override
            public void onError(Object error) {
            }
        });

    }


    private void sendMessage(int id_chat) throws IOException {
        String texto = textFieldMessage.getText().trim();
        if (!texto.trim().isEmpty()){
            messageAPIClient.sendMessageToChat(id_chat, texto, userRegister.getId(), new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    if (!texto.isEmpty()){
                        System.out.println("mensaje enviado");
                        textFieldMessage.setText("");
                        seeMessage(id_chat);

                    }
                }

                @Override
                public void onError(Object error) {

                }
            });
        }
    }

    /**
     * Onclick del boton para que cargye y salga el dialoge y se pueda ver todos los usuarios
     * @throws IOException
     */
    @FXML
    private void showAllUsers() throws IOException {
        // Cargar los datos que va a tener el dialoge
        try {
            listViewDialoge();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cargar del dialoge
        dialoge();
        dialogContent.setHeaderIcon(null);
        dialogContent.setHeaderText("Todos los usuarios registrados en TellMeDam");
        dialog.showDialog();
    }

    /**
     * Configuracion del dialogo
     */
    private void dialoge(){

        this.dialogContent = MFXGenericDialogBuilder.build()
                .setContentText(" ")
                .makeScrollable(true)
                .setContent(contentDialoge())
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(App.s)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Dialogs Preview")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();
    }

    /**
     * Contenido del dialogo
     * @return VBox con el listview y el boton
     */
    public VBox contentDialoge(){
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(listViewAllUser,bt);
        return vb;
    }

    /**
     * List View  que muestra todos los usuarios de la api y tiene un boton que crea nuevos chats
     * @throws IOException
     * @throws InterruptedException
     */
    private void listViewDialoge() throws IOException, InterruptedException {

        ObservableList<User> itemsUser = FXCollections.observableArrayList(arrayListAllUsers);
        listViewAllUser.setItems(itemsUser);

        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User newChatUser = listViewAllUser.getSelectionModel().selectedItemProperty().get();
                if (newChatUser != null){
                    int id = userRegister.getId();
                    Boolean exist = false;
                    for (Chat c: arrayAllChatUsers) {
                        if ((c.getUser2_id() == id && c.getUser1_id() == newChatUser.getId()) ||
                                (c.getUser1_id() == id &&  c.getUser2_id() == newChatUser.getId()) ){
                                exist = true;
                        }
                    }
                    if (!exist) {
                        chatAPIClient.createChat(userRegister.getId(), newChatUser.getId(), new APICallback() {
                            @Override
                            public void onSuccess(Object response) throws IOException {
                                dialog.hide();
                                userchats();
                            }

                            @Override
                            public void onError(Object error) {
                                System.out.println(((Error) error).getError());
                            }
                        });
                    }
                }
            }
        });

    }

    /**
     * Vista de cada celda de los usuarios
     */
    private void editCellViewUsers(ListView<User> userListView){
        userListView.setCellFactory(
                new Callback<ListView<User>, ListCell<User>>() {
                    @Override
                    public ListCell<User> call(ListView<User> param) {
                        return new ListCell<User>() {
                            @Override
                            protected void updateItem(User item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null && !empty) {
                                    try {
                                        ImageView iv = new ImageView(new Image(item.getPhotourl()));
                                        iv.setFitWidth(30);
                                        iv.setFitHeight(30);
                                        setGraphic(iv);
                                    }catch (Exception e){
                                        setText("  ");
                                    }
                                    setText(item.getUsername());
                                } else {
                                    setText(null);
                                    setGraphic(null);
                                }
                            }
                        };
                    }
                });
    }

    private void editCellViewMessage(ListView<Message> userListView) {
        userListView.setCellFactory(
                new Callback<ListView<Message>, ListCell<Message>>() {
                    @Override
                    public ListCell<Message> call(ListView<Message> param) {
                        return new ListCell<Message>() {
                            @Override
                            protected void updateItem(Message item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    VBox vbox = new VBox();
                                    Label contentText = new Label(item.getContent());
                                    contentText.setStyle("-fx-text-fill: white;");
                                    if (item.getIdsender().equals(userRegister.getId()))
                                        vbox.setAlignment(Pos.CENTER_RIGHT);

                                    vbox.getChildren().addAll(contentText);
                                    setGraphic(vbox);
                                    param.scrollTo(param.getItems().size() - 1);
                                }else
                                    setGraphic(null);
                            }
                        };
                    }
                });
    }

    /**
     * Botón para cerrar sesión poniendo el usuario que se registro a null y volviendo a la escena de login
     * @throws IOException
     */
    @FXML
    private void switchToLogin() throws IOException {
        App.userRegister = null;
        App.setRoot("login",450, 500);
    }

    /**
     * Cargar todos los chats en una array
     */
    public void chargeAllChats(){
        chatAPIClient.getAllChatsFromUser(userRegister.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                List<Chat> c = (List<Chat>) response;
                arrayAllChatUsers = new ArrayList<>(c);
            }

            @Override
            public void onError(Object error) {

            }
        });
    }
    public void deleteChat2() throws IOException {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Eliminar Chat");
        MenuItem clearItem = new MenuItem("Limpiar Chat");
        contextMenu.getItems().add(deleteItem);
        contextMenu.getItems().add(clearItem);
        listViewUser.setContextMenu(contextMenu);
        listViewUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(listViewUser, event.getScreenX(), event.getScreenY());
                }
            }
        });
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    cleanChat();
                    deleteChat();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        clearItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    cleanChat();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void deleteChat(){
        User user2 = listViewUser.getSelectionModel().getSelectedItem();
        int idUser1= userRegister.getId();
        int idUser2 = user2.getId();
        for (Chat c : arrayAllChatUsers ) {
            if((c.getUser2_id() == idUser2 && c.getUser1_id() == idUser1) ||(c.getUser2_id() == idUser1 && c.getUser1_id() == idUser2)){
                chatAPIClient.deleteChat(c.getId(), new APICallback() {
                    @Override
                    public void onSuccess(Object response) throws IOException {
                        userchats();
                    }

                    @Override
                    public void onError(Object error) {

                    }
                });
                break;
            }
        }

    }
    public void cleanChat(){
        User user2 = listViewUser.getSelectionModel().getSelectedItem();
        int idUser1= userRegister.getId();
        int idUser2 = user2.getId();
        for (Chat c : arrayAllChatUsers ) {
            if((c.getUser2_id() == idUser2 && c.getUser1_id() == idUser1) ||(c.getUser2_id() == idUser1 && c.getUser1_id() == idUser2)){
                chatAPIClient.cleanChat(c.getId(), new APICallback() {
                    @Override
                    public void onSuccess(Object response) throws IOException {
                        seeMessage(c.getId());
                    }

                    @Override
                    public void onError(Object error) {

                    }
                });

            }
        }
    }


    /**
     * Dialogo para editar el usuario
     * @throws IOException
     */

    public void dialogEditUser() throws IOException {
        dialogeEditUser();
        dialogContentEditUser.setHeaderIcon(null);
        dialogContentEditUser.setHeaderText("Editar perfil");
        dialogEditUser.showDialog();
    }

    private void dialogeEditUser() throws IOException {

        this.dialogContentEditUser = MFXGenericDialogBuilder.build()
                .setContentText(" ")
                .makeScrollable(true)
                .setContent(contentEditUser())
                .get();
        this.dialogEditUser = MFXGenericDialogBuilder.build(dialogContentEditUser)
                .toStageDialogBuilder()
                .initOwner(App.s)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Dialogs Preview")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)

                .get();
    }

    public VBox contentEditUser(){
        VBox vb = new VBox();
        MFXButton buttonBack = styleButton("<- inicio");
        buttonBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialogEditUser.close();
                arrayListAllUsers = App.getAllUsers();
            }
        });
        Label l1 = new Label("Nombre");
        TextField textName = styleTextFieldEdit(userRegister.getUsername());
        Label l2 = new Label("Correo Electronico");
        TextField textMail = styleTextFieldEdit(userRegister.getEmail());
        Label l3 = new Label("Contraseña");
        TextField textPassword = stylePasswordEdit();
        Label l4 = new Label("Repetir Contraseña");
        TextField textPassword2 = stylePasswordEdit();
        Label l5 = new Label("Foto URl");
        TextField textUrl = styleTextFieldEdit(userRegister.getPhotourl());
        MFXButton buttonEdit = styleButton("Aceptar");
        buttonEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String name = textName.getText();
                String email = textMail.getText();
                String password = textPassword.getText();
                String password2 = textPassword2.getText();
                String url = textUrl.getText();
                if(!name.isEmpty() || !email.isEmpty() || !password.isEmpty()|| !url.isEmpty()){
                    if( confirmEmail(email))
                        if(isValidPassword(password,password2)){
                            try {
                                userApiALL.updateUser(userRegister.getId(), textName.getText(), textPassword.getText(), textMail.getText(), textUrl.getText(), new APICallback() {
                                    @Override
                                    public void onSuccess(Object response) throws IOException {
                                        userRegister = (User) response;
                                        dialogEditUser.close();
                                    }

                                    @Override
                                    public void onError(Object error) {
                                        System.out.println(((Error) error).getError());
                                    }
                                });
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }else
                            showAlert("Error", "Contraseñas no coinciden");
                    else
                        showAlert("Error", "Correo electronico invalido");
                }else
                    showAlert("Error","Todos los campos son obligatorios");
            }
        });

        // Agregar márgenes entre los elementos
        Insets margin = new Insets(10, 0, 0, 0);
        VBox.setMargin(l1, margin);
        VBox.setMargin(l2, margin);
        VBox.setMargin(l3, margin);
        VBox.setMargin(l4, margin);
        VBox.setMargin(l5, margin);
        VBox.setMargin(buttonEdit, margin);

        vb.setAlignment(Pos.CENTER_LEFT);
        vb.getChildren().addAll(buttonBack,l1, textName,l2,textMail,l3,textPassword,l4,textPassword2,l5,textUrl,buttonEdit);
        return vb;
    }
    private boolean isValidPassword(String password, String password2) {

        return password.length() >= 8 && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?].*") && password.equals(password2);
    }
    public boolean confirmEmail(String value){

        return value.contains("@") && value.contains(".");
    }

    public MFXButton styleButton(String text){
        MFXButton button = new MFXButton();
        button.setText(text);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: -mfx-green; -fx-border-radius: 3; -fx-text-fill: black;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: -mfx-green; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-border-color: -mfx-green; -fx-border-radius: 3; -fx-text-fill: black;"));
        return button;
    }
    public TextField styleTextFieldEdit(String text){
        TextField textField = new TextField();
        textField.setPromptText(text);
        textField.setPrefWidth(300.0);
        textField.setStyle(
                "-fx-border-color: -mfx-green;"
        );
        return textField;
    }
    public PasswordField stylePasswordEdit(){
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("");
        passwordField.setPrefWidth(300.0);
        passwordField.setStyle(
                "-fx-border-color: -mfx-green;"
        );
        return passwordField;
    }

}
