package com.telchina.pub.image;

/**
 * Presents the reason why image loading and displaying was failed
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public class FailedReason {

    private final FailedType type;

    private final Throwable cause;

    public FailedReason(FailedType type, Throwable cause) {
        this.type = type;
        this.cause = cause;
    }

    /**
     * @return {@linkplain FailedType Fail type}
     */
    public FailedType getType() {
        return type;
    }

    /**
     * @return Thrown exception/error, can be <b>null</b>
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * Presents type of fail while image loading
     */
    public static enum FailedType {
        /**
         * Input/output error. Can be caused by network communication fail or error while caching image on file system.
         */
        IO_ERROR,
        /**
         * Error while
         * {@linkplain android.graphics.BitmapFactory#decodeStream(java.io.InputStream, android.graphics.Rect, android.graphics.BitmapFactory.Options)
         * decode image to Bitmap}
         */
        DECODING_ERROR,
        /**
         * {@linkplain com.nostra13.universalimageloader.core.ImageLoader#denyNetworkDownloads(boolean) Network
         * downloads are denied} and requested image wasn't cached in disk cache before.
         */
        NETWORK_DENIED,
        /**
         * Not enough memory to create needed Bitmap for image
         */
        OUT_OF_MEMORY,
        /**
         * Unknown error was occurred while loading image
         */
        UNKNOWN
    }

    /**
     * Returns a string containing a concise, human-readable description of this
     * object. Subclasses are encouraged to override this method and provide an
     * implementation that takes into account the object's type and data. The
     * default implementation is equivalent to the following expression:
     * <pre>
     *   getClass().getName() + '@' + Integer.toHexString(hashCode())</pre>
     * <p>See <a href="{@docRoot}reference/java/lang/Object.html#writing_toString">Writing a useful
     * {@code toString} method</a>
     * if you intend implementing your own {@code toString} method.
     *
     * @return a printable representation of this object.
     */
    @Override
    public String toString() {
        switch (type){
            case IO_ERROR:
                return "读取或写入图片失败";
            case DECODING_ERROR:
                return "图片解码失败";
            case NETWORK_DENIED:
                return "网络不通";
            case OUT_OF_MEMORY:
                return "内存溢出";
            case UNKNOWN:
                return "未知错误";
        }
        return super.toString();
    }
}