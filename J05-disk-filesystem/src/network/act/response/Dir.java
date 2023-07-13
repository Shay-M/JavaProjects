package network.act.response;


public record Dir(simplefilesystem.FileSystem fileSystem) implements Action {

    @Override
    public Result doAction(final String[] split) {
        return new Actionresult(ResultStatus.OK, fileSystem.dir().toString());
    }
}