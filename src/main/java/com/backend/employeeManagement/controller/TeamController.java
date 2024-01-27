package com.backend.employeeManagement.controller;

import com.backend.employeeManagement.models.Team;
import com.backend.employeeManagement.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:3002"})
public class TeamController {
    @Autowired
    private TeamService teamService;
    @PostMapping
    public void saveTeam(@RequestBody Team team){
        teamService.saveTeam(team);
    }

    @GetMapping
    public List<Team> getTeamDetails(){
        return teamService.getTeamDetails();
    }
    @DeleteMapping("/{team_id}")
    public void deleteTeam(@PathVariable int team_id){
        teamService.deleteTeam(team_id);
    }

    @GetMapping("/resetAutoIncrement")
    public ResponseEntity<String> resetAutoIncrement() {
        teamService.resetAutoIncrement();
        return ResponseEntity.ok("Auto-increment sequence reset successfully.");
    }
}
