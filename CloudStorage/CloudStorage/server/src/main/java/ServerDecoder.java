import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;


public class ServerDecoder extends ChannelInboundHandlerAdapter {
    private static String pathOfDirectories = "";
    private static String pathsOfDirectories = "";
    private static final LinkedList<File> list = new LinkedList<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null)
            return;
        if (msg instanceof UpdateMsg) {
            UpdateMsg updateMsg = (UpdateMsg) msg;
            String receivedLogin = updateMsg.getLogin();
            ctx.writeAndFlush(new UpdateMsg(getContentsOfCloudStorage(receivedLogin)));

        } else if (msg instanceof DeleteMsg) {
            DeleteMsg deleteMsg = (DeleteMsg) msg;
            for (int i = 0; i < deleteMsg.getFilesToDelete().size(); i++) {
                File fileToDelete = new File(deleteMsg.getFilesToDelete().get(i).getAbsolutePath());
                if (fileToDelete.isDirectory()) {
                    deleteRecursively(fileToDelete);
                } else
                    fileToDelete.delete();

            }
            deleteMsg.getFilesToDelete().clear();
            if (deleteMsg.getFilesToDelete().isEmpty())
                ctx.writeAndFlush(new UpdateMsg(getContentsOfCloudStorage(deleteMsg.getLogin())));
            else
                ctx.writeAndFlush("Delete has failed.");
        } else if (msg instanceof FileListMsg) {
            FileListMsg fileListMsg = (FileListMsg) msg;
            for (int i = 0; i < fileListMsg.getFileListMsg().size(); i++) {
                File file = new File(fileListMsg.getFileListMsg().get(i).getAbsolutePath());
                Path fileToSend = Paths.get(fileListMsg.getFileListMsg().get(i).getAbsolutePath());
                try {
                    if (file.isDirectory()) {
                        if (file.listFiles().length == 0)
                            ctx.writeAndFlush(new FileSendingMsg(file.getName(), true, true));
                        else ctx.writeAndFlush(new FileSendingMsg(file.getName(), true, false));
                    } else {
                        try {
                            ctx.writeAndFlush(new FileSendingMsg(fileToSend));
                        } catch (AccessDeniedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (msg instanceof FileSendingMsg) {
            FileSendingMsg fileSendingMsg = (FileSendingMsg) msg;
            Path pathToNewFile = Paths.get("server/CloudStorage/" + fileSendingMsg.getLogin() + File.separator + fileSendingMsg.getFilename());
            if (fileSendingMsg.isDirectory() && fileSendingMsg.isEmpty()) {
                if (Files.exists(pathToNewFile)) {
                    System.out.println("File with this name is already exists.");
                } else
                    Files.createDirectory(pathToNewFile);

            } else {
                if (Files.exists(pathToNewFile))
                    System.out.println("File with this name is already exists.");
                else
                    Files.write(Paths.get("server/CloudStorage/" + fileSendingMsg.getLogin() + File.separator + fileSendingMsg.getFilename()), fileSendingMsg.getData(), StandardOpenOption.CREATE);

            }
            ctx.writeAndFlush(new UpdateMsg(getContentsOfCloudStorage(fileSendingMsg.getLogin())));
        } else if (msg instanceof AuthorizationMsg) {
            AuthorizationMsg authMsg = (AuthorizationMsg) msg;
            DBHandler.getConnectionWithDB();
            if (DBHandler.checkIfUserExistsForAuthorization(authMsg.getLogin()) && DBHandler.checkIfPasswordIsRight(authMsg.getLogin(), authMsg.getPassword()))
                ctx.writeAndFlush("User" + authMsg.getLogin() + " has logged.");
            else ctx.writeAndFlush("Incorrect data.");
            DBHandler.disconnectDB();
        } else if (msg instanceof RegistrationMsg) {
            RegistrationMsg registrationMsg = (RegistrationMsg) msg;
            DBHandler.getConnectionWithDB();
            if (DBHandler.checkIfUserExistsForAuthorization(registrationMsg.getLogin()))
                ctx.writeAndFlush("User with that login already exists.");
            else {
                DBHandler.registerNewUser(registrationMsg.getLogin(), registrationMsg.getPassword());
                File newDirectory = new File("server/CloudStorage/" + registrationMsg.getLogin());
                newDirectory.mkdir();
                ctx.writeAndFlush("User successfully registered!");
            }
            DBHandler.disconnectDB();
        }
    }

    private void deleteRecursively(File f) throws Exception {
        try {
            if (f.isDirectory()) {
                for (File list : f.listFiles()) {
                    deleteRecursively(list);
                }

            }
            if (!f.delete()) {
                throw new Exception("Delete operation has failed for the file: " + f);
            }
        } catch (Exception e) {
            throw new Exception("Delete operation has failed for the file: " + f);
        }
    }

    private HashMap<Integer, LinkedList<File>> getContentsOfCloudStorage(String login) {
        HashMap<Integer, LinkedList<File>> cloudStorageContents;
        LinkedList<File> listCloudStorageFiles = new LinkedList<>();
        File path = new File("server/CloudStorage/" + login);
        File[] files = path.listFiles();
        cloudStorageContents = new HashMap<>();
        if (files.length == 0)
            cloudStorageContents.clear();
        else {
            listCloudStorageFiles.clear();
            listCloudStorageFiles.addAll(Arrays.asList(files));
            cloudStorageContents.clear();
            cloudStorageContents.put(0, listCloudStorageFiles);
        }
        return cloudStorageContents;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}