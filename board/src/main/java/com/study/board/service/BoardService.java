package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired // spring bean 이 자동으로 주입해줌
    private BoardRepository boardRepository;

    // 게시물 조회
    public List<Board> getList() {
        return boardRepository.findAll();
    }

    // 게시물 상세 조회
    public Board getDetail(Integer id) throws Exception{
        Optional<Board> board = boardRepository.findById(id);
        if(board != null && board.isPresent()){
            return board.get();
        }
        return null;
    }

    // 게시물 작성
    public void write(Board board) {
        boardRepository.save(board);
    }



}
