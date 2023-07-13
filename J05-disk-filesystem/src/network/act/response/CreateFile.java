package network.act.response;

import simplefilesystem.FileSystem;

public record CreateFile(FileSystem fileSystem) implements Action {
    @Override
    public Result doAction(final String[] split) {
        try {
            final var success = fileSystem.createFile(split[1]);
            if (success) {
                final var file = fileSystem.open(split[1]);
                file.write(split[2]);
                return new Actionresult(ResultStatus.OK, "File created");
            }
            else {
                return new Actionresult(ResultStatus.FAIL, "have this file");
            }
        }
        catch (Exception ex) {
            return new Actionresult(ResultStatus.FAIL, ex.getMessage());
        }
    }
}
