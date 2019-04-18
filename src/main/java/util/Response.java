package util;

public class Response {
    private boolean state;
    private Object data;

    public Response setState(boolean state) {
        this.state = state;
        return this;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{")
                .append("state: " + this.state + ", ")
                .append("data: " + this.data).toString();
    }

    public boolean isState() {
        return state;
    }

    public Object getData() {
        return data;
    }
}
