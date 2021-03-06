package com.anarsultanov.simpletexteditor.core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class WPTree {

    // this is the root node of the WPTree
    private WPTreeNode root;
    // used to search for nearby Words
    private NearbyWords nw;

    public WPTree(NearbyWords nw) {
        this.root = null;
        this.nw = nw;
    }

    public List<String> findPath(String word1, String word2) {
        List<WPTreeNode> queue = new LinkedList<>();     // String to explore
        HashSet<String> visited = new HashSet<>();   // to avoid exploring the same string multiple times

        root = new WPTreeNode(word1, null);
        queue.add(root);
        visited.add(word1);

        while (!queue.isEmpty()) {
            WPTreeNode curr = queue.remove(0);
            List<String> n = nw.distanceOne(curr.getWord(), true);
            for (String s : n) {
                if (!visited.contains(s)) {
                    WPTreeNode next = curr.addChild(s);
                    visited.add(s);
                    queue.add(next);
                    if (s.equals(word2)) {
                        return next.buildPathToRoot();
                    }
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    private class WPTreeNode {

        private String word;
        private List<WPTreeNode> children;
        private WPTreeNode parent;

        /**
         * Construct a node with the word w and the parent p
         * (pass a null parent to construct the root)
         *
         * @param w The new node's word
         * @param p The new node's parent
         */
        private WPTreeNode(String w, WPTreeNode p) {
            this.word = w;
            this.parent = p;
            this.children = new LinkedList<>();
        }

        /**
         * Add a child of a node containing the String s
         * precondition: The word is not already a child of this node
         *
         * @param s The child node's word
         * @return The new WPTreeNode
         */
        private WPTreeNode addChild(String s) {
            WPTreeNode child = new WPTreeNode(s, this);
            this.children.add(child);
            return child;
        }

        /**
         * Get the list of children of the calling object
         * (pass a null parent to construct the root)
         *
         * @return List of WPTreeNode children
         */
        private List<WPTreeNode> getChildren() {
            return this.children;
        }

        /**
         * Allows you to build a path from the root node to
         * the calling object
         *
         * @return The list of strings starting at the root and
         * ending at the calling object
         */
        private List<String> buildPathToRoot() {
            WPTreeNode curr = this;
            List<String> path = new LinkedList<>();
            while (curr != null) {
                path.add(0, curr.getWord());
                curr = curr.parent;
            }
            return path;
        }

        /**
         * Get the word for the calling object
         *
         * @return Getter for calling object's word
         */
        private String getWord() {
            return this.word;
        }

        /**
         * toString method
         *
         * @return The string representation of a WPTreeNode
         */
        public String toString() {
            StringBuilder ret = new StringBuilder("Word: " + word + ", parent = ");
            if (this.parent == null) {
                ret.append("null.\n");
            } else {
                ret.append(this.parent.getWord()).append("\n");
            }
            ret.append("[ ");
            for (WPTreeNode curr : children) {
                ret.append(curr.getWord()).append(", ");
            }
            ret.append(" ]\n");
            return ret.toString();
        }
    }
}