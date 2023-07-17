package shay.finalproject.datastructure.infrastructure;

import java.util.List;

public interface Data<T> {

    void add(final T word, final LinkWordCount linkWordCount);

    List<LinkWordCount> getLinkWordCount(final T word);

    boolean contains(final T word);

    int size();

}
