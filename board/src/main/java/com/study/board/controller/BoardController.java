package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardservice;

    // 게시물 리스트 조회
    @GetMapping("/board/list") // localhost:8080/board/write
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> list = boardservice.getList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4 ,1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list",list );
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    // 게시물 상세 조회
    @GetMapping("/board/view") // localhost:8080/board/detail?id=2
    public String boardDetailList(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("board", boardservice.boardView(id));
        return "boardview";
    }

    // 게시물 작성
    @GetMapping("/board/write") // localhost:8080/board/write
    public String boardWriteForm() {
        return "boardwrite";
    }

    // 게시물 작성 버튼
    @PostMapping("/board/writepro")
    public String boardWriteProcess(Board board, @RequestParam(name="file", required = false) MultipartFile file) throws Exception {
        boardservice.write(board, file);
        System.out.println("제목" + board.getTitle());
        return "redirect:/board/list";
    }

    // 게시물 삭제 버튼
    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id") Integer id) {
        boardservice.delete(id);
        return "redirect:/board/list";
    }

    // 수정버튼 -> 게시물 상세로 이동
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardservice.boardView(id));
        return "boardmodify";
    }


    // 게시물 수정
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, @RequestParam(name="file", required = false) MultipartFile file) throws Exception{

        Board boardTemp = boardservice.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardservice.write(boardTemp, file);

        return "redirect:/board/list";
    }
}
