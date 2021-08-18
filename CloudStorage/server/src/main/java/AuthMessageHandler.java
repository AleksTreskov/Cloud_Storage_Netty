import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;


public class AuthMessageHandler extends ChannelInboundHandlerAdapter {

    private boolean isAuthorized;
    private String nickname;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        System.out.println("AuthMessageHandler received message!");
        if (msg == null)
            return;
        else {
            if (!isAuthorized) {

                if (msg instanceof AuthorizationMsg) {
                    AuthorizationMsg am = (AuthorizationMsg) msg;
                    nickname = DBHandler.getNickname(am.getLogin(), am.getPassword());
                    if (nickname != null) {
                        isAuthorized = true;
                        ctx.fireChannelRead(new AuthorizationMsg(nickname));

                        ReferenceCountUtil.release(msg);
                    }
                } else {
                    ReferenceCountUtil.release(msg);
                }
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
