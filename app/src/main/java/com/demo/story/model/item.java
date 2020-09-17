package com.demo.story.model;

public class item {
    private String storyTitle;
    private String storyDescription;
    private boolean isItemAdded;

    public item() {
    }
    public item(String title, String description, boolean isItemAdded) {
        this.storyTitle = title;
        this.storyDescription = description;
        this.isItemAdded = isItemAdded;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryDescription() {
        return storyDescription;
    }

    public void setStoryDescription(String storyDescription) {
        this.storyDescription = storyDescription;
    }

    public void setIsItemAdded(boolean itemAdded) {
        isItemAdded = itemAdded;
    }
    public boolean getIsItemAdded() {
        return isItemAdded;
    }
}
