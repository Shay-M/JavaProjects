package network.act.response;

import simplefilesystem.FileSystem;

import java.util.ArrayList;

record File(String name, int size) {
}

public record InfoFile(FileSystem fileSystem) implements Action {
    @Override
    public Result doAction(final String[] split) {
        final var files = new ArrayList<File>();

        final var file = fileSystem.open(split[1]);
        files.add(new File(file.getFileName(), file.getInode().getSize()));

        return new FileList(ResultStatus.OK, files);
    }
}
