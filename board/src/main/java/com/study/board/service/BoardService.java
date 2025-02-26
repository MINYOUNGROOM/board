package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


@Service
public class BoardService {

    @Autowired // spring bean 이 자동으로 주입해줌
    private BoardRepository boardRepository;

    // 게시물 글 작성
    public void write(Board board, MultipartFile file) throws Exception {
        String projectPath = System.getProperty("user.dir")+"\\board\\src\\main\\resources\\static\\files"; // 프로젝트 경로를 projectPath에 담는것


        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename(); // 고유한 번호 + 본 파일 이름
        File saveFile = new File(projectPath, fileName);

        System.out.println("projectPath : " + projectPath); // 파일 저장 경로

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    // 게시물 조회
    public Page<Board> getList(Pageable pageable) {
        return boardRepository.findAll(pageable);
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
