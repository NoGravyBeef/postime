package com.green.todo.tag;

import com.green.todo.common.CommonUtils;
import com.green.todo.tag.model.req.TagPostReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/tag")
@Tag(name = "태그 컨트롤러")
public class TagController {
    private final TagService service;
    private final CommonUtils utils;

//    @PostMapping
//    public int test() {
//        List<Long> list1 = new ArrayList<>();
//        list1.add(100L);
//        List<TagPostReq> list2 = new ArrayList<>();
////        TagPostReq p1 = new TagPostReq();
////        p1.setTitle("태그테스트1");
////        p1.setColor(1);
////        p1.setCalendarId(1L);
////
////        TagPostReq p2 = new TagPostReq();
////        p2.setTitle("태그테스트2");
////        p2.setColor(2);
////        p2.setCalendarId(1L);
//
////        list2.add(p1);
////        list2.add(p2);
//        int r = service.tagPost(list1, list2, 1);
//        return r;
//    }
//
//    @DeleteMapping
//    public int del() {
//        List<Long> list1 = new ArrayList<>();
//        list1.add(100L);
//        int r = service.tagDelete(list1,1);
//        return r;
//    }


}
