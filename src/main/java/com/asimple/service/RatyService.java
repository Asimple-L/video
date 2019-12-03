package com.asimple.service;

import com.asimple.entity.Film;
import com.asimple.entity.Raty;
import com.asimple.mapper.RatyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName video
 * @description 评分服务层实现
 * @author Asimple
 */
@Service
public class RatyService {
    @Resource
    private RatyMapper ratyMapper;
    @Resource
    private FilmService filmService;

    /**
     * 统计影片评分总数
     * @param filmId 影片id
     * @return 返回影片总数
     */
    public int getCountByFilmId(String filmId) {
        return ratyMapper.getCountByFilmId(filmId);
    }

    /**
     * 添加评分
     * @param raty 评分实体
     * @return 添加成功返回true
     */
    public double add(Raty raty) {
        int cnt = ratyMapper.add(raty);
        if( cnt == 1 ) {
            // 添加成功，重新计算评分
            List<Raty> ratyList = listByFilmId(raty.getFilm_id());
            long count = 0;
            for (Raty raty1 : ratyList) {
                count = count + Integer.parseInt(raty1.getScore());
            }
            long tem = count / ratyList.size();
            double evaluation = Math.floor(tem * 10d) / 10;
            Film film = filmService.load(raty.getFilm_id());
            film.setEvaluation(evaluation);
            // 更新最新的评分
            if( filmService.update(film) ) {
                return evaluation;
            }
        }
        return -1;
    }

    /**
     * 获取评分列表
     * @param filmId 影片id
     * @return 评分列表
     */
    public List<Raty> listByFilmId(String filmId) {
        return ratyMapper.listByFilmId(filmId);
    }
}
