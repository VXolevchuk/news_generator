package com.gmail.vx.rnd_news_generator.services.sorter;

import com.gmail.vx.rnd_news_generator.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class ShellSorter {
    public  void sortByComments(Subject [] array) {
        int h = 1;
        while (h*3 < array.length)
            h = h * 3 + 1;

        while(h >= 1) {
            hSortByComments(array, h);
            h = h/3;
        }
    }

    public  void sortByLikes(Subject [] array) {
        int h = 1;
        while (h*3 < array.length)
            h = h * 3 + 1;

        while(h >= 1) {
            hSortByLikes(array, h);
            h = h/3;
        }
    }

    private static void hSortByComments(Subject [] array, int h) {
        int length = array.length;
        for (int i = h; i < length; i++) {
            for (int j = i; j >= h; j = j - h) {
                if (array[j].countComments() > array[j - h].countComments())
                    swap(array, j, j - h);
                else
                    break;
            }
        }
    }

    private static void hSortByLikes(Subject [] array, int h) {
        int length = array.length;
        for (int i = h; i < length; i++) {
            for (int j = i; j >= h; j = j - h) {
                if (array[j].countLikes() > array[j - h].countLikes())
                    swap(array, j, j - h);
                else
                    break;
            }
        }
    }


    private static void swap(Subject[] array, int i, int j) {
        Subject temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
