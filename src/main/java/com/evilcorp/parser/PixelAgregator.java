package com.evilcorp.parser;

import com.evilcorp.entity.DiffGroup;
import com.evilcorp.entity.Pixel;

import java.util.ArrayList;
import java.util.List;

import static com.evilcorp.utils.ImageUtils.getDistanceBetweenTwoPoints;

public class PixelAgregator {

    private static final double PIXEL_DISTANCE = 70;

    public List<DiffGroup> getPixelGroups(List<Pixel> diffPixels) {
        List<DiffGroup> diffGroups = new ArrayList<>();
        for (int mainCounter = 0; mainCounter < diffPixels.size(); mainCounter++) {
            for(int secondCounter = mainCounter + 1; secondCounter < diffPixels.size(); secondCounter++) {
                double distance = getDistanceBetweenTwoPoints(diffPixels.get(mainCounter), diffPixels.get(secondCounter));
                if(distance <= PIXEL_DISTANCE) {
                    addPixelToGroup(diffGroups, diffPixels.get(mainCounter), diffPixels.get(secondCounter));
                }
            }
        }
        return diffGroups;
    }

    private void addPixelToGroup(List<DiffGroup> diffGroups, Pixel firstPixel, Pixel secondPixel) {
        IndexHolder indexOfGroups = getIndexOfGroup(diffGroups, firstPixel, secondPixel);
        if(indexOfGroups.getFirstIndex() == -1 && indexOfGroups.getSecondIndex() == -1) {
            DiffGroup diffGroup = new DiffGroup();
            diffGroup.addPixels(firstPixel, secondPixel);
            diffGroups.add(diffGroup);
        } else if(indexOfGroups.getFirstIndex() == -1) {
            diffGroups.get(indexOfGroups.getSecondIndex()).addPixel(firstPixel);
        } else if(indexOfGroups.getSecondIndex() == -1) {
            diffGroups.get(indexOfGroups.getFirstIndex()).addPixel(secondPixel);
        }
    }

    private IndexHolder getIndexOfGroup(List<DiffGroup> diffGroups, Pixel firstPixel, Pixel secondPixel) {
        IndexHolder indexHolder = new IndexHolder(-1, -1);
        for(int index = 0; index < diffGroups.size(); index++) {
            DiffGroup diffGroup = diffGroups.get(index);
            if(diffGroup.getPixels().contains(firstPixel)) {
                indexHolder.setFirstIndex(index);
            }
            if(diffGroup.getPixels().contains(secondPixel)) {
                indexHolder.setSecondIndex(index);
            }
            if(indexHolder.bothSetted()) {
                break;
            }
        }
        return indexHolder;
    }

    private class IndexHolder {
        private int firstIndex;
        private int secondIndex;

        public IndexHolder(int firstIndex, int secondIndex) {
            this.firstIndex = firstIndex;
            this.secondIndex = secondIndex;
        }

        public boolean bothSetted() {
            return firstIndex > -1 && secondIndex > -1;
        }

        public int getFirstIndex() {
            return firstIndex;
        }

        public void setFirstIndex(int firstIndex) {
            this.firstIndex = firstIndex;
        }

        public int getSecondIndex() {
            return secondIndex;
        }

        public void setSecondIndex(int secondIndex) {
            this.secondIndex = secondIndex;
        }
    }
}
