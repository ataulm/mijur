package com.ataulm.mijur.data.parser;

import com.ataulm.mijur.data.Comment;
import com.ataulm.mijur.data.Comments;
import com.ataulm.mijur.data.Time;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommentParser {

    private final Gson gson;

    private CommentParser(Gson gson) {
        this.gson = gson;
    }

    public static CommentParser newInstance() {
        return new CommentParser(new Gson());
    }

    public Comments parse(InputStream stream) {
        JsonReader reader = new JsonReader(new InputStreamReader(stream));
        GsonCommentResponse gsonCommentResponse = gson.fromJson(reader, GsonCommentResponse.class);
        return parse(gsonCommentResponse);
    }

    private Comments parse(GsonCommentResponse gsonCommentResponse) {
        if (gsonCommentResponse.success) {
            List<Comment> commentsList = parseAllComments(gsonCommentResponse.data);
            return new Comments(commentsList);
        }
        return Comments.empty();
    }

    /**
     * Parses all comments into a flat List<Comment>.
     * <p/>
     * If a comment has a reply, they will be siblings in this list.
     */
    private List<Comment> parseAllComments(List<GsonComment> gsonComments) {
        // initial size is naive; assumes no replies but it's best case scenario
        List<Comment> comments = new ArrayList<Comment>(gsonComments.size());
        for (GsonComment gsonComment : gsonComments) {
            Comment comment = parseGsonComment(gsonComment);
            comments.add(comment);
            if (gsonCommentHasReplies(gsonComment)) {
                List<Comment> replies = parseAllComments(gsonComment.children);
                comments.addAll(replies);
            }
        }
        return comments;
    }

    private boolean gsonCommentHasReplies(GsonComment gsonComment) {
        return gsonComment.children != null && gsonComment.children.size() > 0;
    }

    private Comment parseGsonComment(GsonComment gsonComment) {
        return new Comment(gsonComment.id,
                gsonComment.author,
                gsonComment.upvotes,
                gsonComment.downvotes,
                gsonComment.text,
                gsonComment.parentId,
                new Time(gsonComment.datetime));
    }

}
