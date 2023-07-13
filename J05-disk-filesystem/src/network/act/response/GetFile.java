package network.act.response;

import simplefilesystem.File;
import simplefilesystem.FileSystem;


public record GetFile(FileSystem fileSystem) implements Action {
    @Override
    public Result doAction(final String[] split) {
        final File file;
        if (split.length > 1) {
            try {
                file = fileSystem.open(split[1]);
            }
            catch (Exception ex) {
                return new Actionresult(ResultStatus.FAIL, ex.getMessage());
            }
            return new FileContent(ResultStatus.OK, file.readString().getBytes());
        }
        return new Actionresult(ResultStatus.FAIL, "Give Name!");
    }
}
