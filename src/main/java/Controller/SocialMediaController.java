package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //GET
        app.get("example-endpoint", this::exampleHandler);
        app.get("messages", this::getAllMessageHandler);
        app.get("accounts/{id}/messages", this::getAllUserMessageHandler);
        app.get("messages/{id}", this::getMessageFromMessageIDHandler);
    
        //POST
        app.post("messages", this::postMessageHandler); 
        app.post("register", this::postUserHandler);
        app.post("login", this::login);
    
        //PATCH
        app.patch("messages/{id}", this::patchMessageHandler);
    
        //DELETE
        app.delete("messages/{id}", this::deleteMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    //GET
    private void getAllMessageHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    private void getAllUserMessageHandler(Context ctx) {
        ctx.json("test");
    }

    private void getMessageFromMessageIDHandler(Context ctx) {
        int messageID = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(messageService.getMessage(messageID));
        ctx.status(200);
    }

    
    //POST - CREATE
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message result = messageService.createMessage(message);
        if(result != null) {
            ctx.json(om.writeValueAsString(result));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void postUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account result = accountService.registerUser(account);
        if(result != null) {
            ctx.json(om.writeValueAsString(result));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    private void login(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account result = accountService.loginUser(account);
        if(result != null) {
            ctx.json(om.writeValueAsString(result));
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }

    //PATCH
    private void patchMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        int messageID = Integer.parseInt(ctx.pathParam("id"));
        message.setMessage_id(messageID);

        

        ctx.json("test");
    }

    //REMOVE
    private void deleteMessageHandler(Context ctx) {
        int messageID = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(messageService.deleteMessage(messageID));
        ctx.status(200);
    }

}