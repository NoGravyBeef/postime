package com.green.todo.tag;

import com.green.todo.tag.model.TagEntity;
import com.green.todo.tag.model.req.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    int insertTag(TagPostReq p);
    int insertBoardTag(BoardTagPostReq p);

    List<TagEntity> getTagsByBoardId(Long BoardId);
    TagEntity getTagForCheckTitle(TagGetReq p);
    List<Long> getTagByBoardId(long boardId);

    int deleteTagPermanent(long tagId);
    int deleteBoardTag(List<BoardTagDeleteReq> p);
}