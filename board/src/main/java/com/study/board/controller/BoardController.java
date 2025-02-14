package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardservice;

    // 게시물 리스트 조회
    @GetMapping("/board/list") // localhost:8080/board/write
    public String boardList(Model model) {
        model.addAttribute("list", boardservice.getList());
        return "boardlist";
    }

    // 게시물 상세 조회
    @GetMapping("/board/detail/{id}") // localhost:8080/board/detail?id=2
    public String boardDetailList(Model model, Integer id) throws Exception{
        model.addAttribute("detail", boardservice.getDetail(id));
        return "boardview";
    }

    // 게시물 작성
    @GetMapping("/board/write") // localhost:8080/board/write
    public String boardWriteForm() {
        return "boardwrite";
    }

    // 게시물 작성 버튼
    @PostMapping("/board/writeprocess")
    public String boardWriteProcess(Board board) {
        boardservice.write(board);
        System.out.println("제목" + board.getTitle());
        return "";
    }


}
