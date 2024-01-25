package com.backend.employeeManagement.service;

import com.backend.employeeManagement.models.Team;
import com.backend.employeeManagement.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;
    public void saveTeam(Team team){
        teamRepository.save(team);
    }
    public List<Team> getTeamDetails(){
        return teamRepository.findAll();
    }
    public void deleteTeam(int team_id){
        teamRepository.deleteById(team_id);
    }
    public void resetAutoIncrement() {
        teamRepository.resetAutoIncrement();
    }
}
