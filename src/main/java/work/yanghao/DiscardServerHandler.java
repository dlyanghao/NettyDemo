package work.yanghao;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelHandlerAdapter {

    /**
     * 这个方法会在发生异常时触发
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //出现异常关闭
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 这里我们覆盖了chanelRead()事件处理方法。 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用，
     * 这个例子中，收到的消息的类型是ByteBuf
     *
     * @param ctx
     *            通道处理的上下文信息
     * @param msg
     *            接收的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf in = (ByteBuf)msg;
            //对客户端输入进行输出
            System.out.println(in.toString(CharsetUtil.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //让Netty抛弃接收到的消息
            ReferenceCountUtil.release(msg);
        }
    }
}
