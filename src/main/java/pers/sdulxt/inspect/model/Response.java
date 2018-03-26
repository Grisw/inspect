package pers.sdulxt.inspect.model;

import java.io.Serializable;

/**
 * General response object.
 * @param <T> The type of the content in the response.
 */
public class Response<T> implements Serializable{

    /**
     * The Code object indicates the status code and the description.
     * Use static method {@code getCode} to find the specific code enumeration by status number.
     */
    public enum Code{
        SUCCESS(0, "Success."),
        UNKNOWN_ERROR(-1, "Unknown error.");

        private int code;
        private String message;

        Code(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        /**
         * Get specific code enumeration by status number.
         * @param code status number
         * @return Code enumeration object
         */
        public static Code getCode(int code){
            Code[] codes = Code.values();
            for(Code c : codes){
                if(c.code == code){
                    return c;
                }
            }
            return Code.UNKNOWN_ERROR;
        }
    }

    private int code;
    private String message;
    private T body;

    public Response(Code code, T body) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
