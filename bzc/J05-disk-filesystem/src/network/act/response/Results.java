package network.act.response;

import java.util.ArrayList;

record StatusResult(ResultStatus status) implements Result {
}

record Actionresult(ResultStatus status, String message) implements Result {
}

record FileContent(ResultStatus result, byte[] data) implements Result {
}

record FileList(ResultStatus result, ArrayList<File> files) implements Result {
}


// public record Result(ResultStatus resultStatus, String Data) {
//
// }

/*record Actionresult(ResultStatus status, String message) implements Result {
}

record FileContent(Actionresult result, byte[] data) implements Result {
}

record FileList(Actionresult result, ArrayList<File> files) implements Result {*/



