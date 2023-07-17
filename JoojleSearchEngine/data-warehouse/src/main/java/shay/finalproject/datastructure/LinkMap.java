package shay.finalproject.datastructure;

import shay.finalproject.datastructure.infrastructure.Data;
import shay.finalproject.datastructure.infrastructure.LinkWordCount;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


public class LinkMap<T> implements Data<T> {
    final ConcurrentHashMap<T, CopyOnWriteArraySet<LinkWordCount>> map = new ConcurrentHashMap<>();

    @Override
    public void add(final T word, final LinkWordCount linkWordCount) {
        // todo validate
        Objects.requireNonNull(word);
        Objects.requireNonNull(linkWordCount);
        map.computeIfAbsent(word, k -> new CopyOnWriteArraySet<>()).add(linkWordCount);
    }

    @Override
    public List<LinkWordCount> getLinkWordCount(final T word) {
        return List.copyOf(map.getOrDefault(word, new CopyOnWriteArraySet<>()));
    }

    @Override
    public boolean contains(final T word) {
        Objects.requireNonNull(word);
        return map.containsKey(word); //?
    }

    @Override
    public int size() {
        return map.size();
    }

    // remove it
    public ConcurrentHashMap<T, CopyOnWriteArraySet<LinkWordCount>> getMap() {
        return map;
    }

}


