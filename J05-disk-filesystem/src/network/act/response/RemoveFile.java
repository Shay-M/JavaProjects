package network.act.response;

public record RemoveFile(simplefilesystem.FileSystem fileSystem) implements Action {


    @Override
    public Result doAction(final String[] split) {
        try {
            fileSystem.deleteFile(split[1]);
        }
        catch (Exception ex) {
            return new Actionresult(ResultStatus.FAIL, ex.getMessage());
        }
        return new StatusResult(ResultStatus.OK);
    }
}