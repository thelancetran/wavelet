import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<>(5);
    String to_Print = "";
    String[] parameters;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            for(String s: list) {
                to_Print = to_Print + s + " ";
            }
            return String.format("Lance's List: " + to_Print);
        }
        else if (url.getPath().contains("/search")) {
            parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String isContained = "";
                for(String s: list) {
                    if (s.indexOf(parameters[1]) != -1) {
                        isContained = isContained + s + " ";
                    }
                }
                return String.format(isContained);
            }
            return String.format("Try /search?s=...");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    list.add(parameters[1]);
                    return String.format("%s has been added", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}