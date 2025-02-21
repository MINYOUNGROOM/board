package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
public class BoardService {

    @Autowired // spring bean 이 자동으로 주입해줌
    private BoardRepository boardRepository;

    // 게시물 글 작성
    public void write(Board board, MultipartFile file) throws Exception {
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files"; // 프로젝트 경로를 projectPath에 담는것

        // 폴더가 존재하지 않으면 생성
        File directory = new File(projectPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename(); // 고유한 번호 + 본 파일 이름
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);
        boardRepository.save(board);
    }

    // 게시물 조회
    public List<Board> getList() {
        return boardRepository.findAll();
    }

    // 게시물 상세 조회
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }


    // 게시물 삭제
    public void delete(Integer id){
        boardRepository.deleteById(id);
    }


}
