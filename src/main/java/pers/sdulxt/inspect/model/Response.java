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
        UNKNOWN_ERROR(-1, "Unknown error."),
        SUCCESS(0, "Success."),
        WRONG_CREDENTIALS(11, "Wrong password or non-existent account."),
        USER_EXISTS(12, "The phone number was already exists."),
        PARAMS_ERROR(100, "Some required parameters are not found or in bad format."),
        TOKEN_EXPIRED(200, "The token is expired, requires login."),
        UNKNOWN_REQUEST(300, "Unknown request."),
        ACCESS_REJECT(400, "You have no access to the resource."),
        RESOURCE_NOT_FOUND(404, "The requested resource is not found.");

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

    public Response(T body) {
        this.code = Code.SUCCESS.getCode();
        this.message = Code.SUCCESS.getMessage();
        this.body = body;
    }

    public Response(Code code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.body = null;
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
