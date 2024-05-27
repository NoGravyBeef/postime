package com.green.todo.itemContent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/item/content")
@RequiredArgsConstructor
public class ItemContentController {
    private final ItemContentService service;

}
