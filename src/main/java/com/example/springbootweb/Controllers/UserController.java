package com.example.springbootweb.Controllers;

import com.example.springbootweb.DTOS.LoginDTO;
import com.example.springbootweb.DTOS.Movies;
import com.example.springbootweb.DTOS.SearchDTO;
import com.example.springbootweb.JDBC.JdbcConnect;
import com.example.springbootweb.Models.MovieModel;
import com.example.springbootweb.Models.TrackingId;
import com.example.springbootweb.Models.User;
import com.example.springbootweb.Respository.MovieRepository;
import com.example.springbootweb.Respository.TrackingRepository;
import com.example.springbootweb.Respository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private JdbcConnect jdbcConnect;
    @GetMapping(value ={"/login"})
    public String home(Model model){
        model.addAttribute("user",new LoginDTO());
        return "login";
    }
//    @GetMapping(value = "/register")
//    public String register(Model model){
//        model.addAttribute("user",new RegisterDTO());
//        return "register";
//    }
    @GetMapping(value = {"/movies","/"})
    public String homePage(Model model,@RequestParam(value = "search",defaultValue = "") String search,@CookieValue(value = "TrackingId",defaultValue = "1") String trackingId) throws SQLException,IllegalArgumentException, JsonProcessingException {
        String uri = null;
        if(search.trim().isEmpty()){
            uri = "https://api.themoviedb.org/3/movie/popular?api_key=6460de6560376b65422bf1e31a0d4e21&language=en-US&page=1";
        }else {
            uri = "https://api.themoviedb.org/3/search/movie?api_key=6460de6560376b65422bf1e31a0d4e21&language=en-US&page=1&query="+search;
        }
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(uri, String.class);
        Movies movies = new Gson().fromJson(json,Movies.class);
        model.addAttribute("movies",movies.getResults());
        model.addAttribute("searchItem",new SearchDTO());
        return "movie";
    }
    @GetMapping(value = "/trend")
    public String trend(Model model,@RequestParam(value = "id",defaultValue = "") String id) throws SQLException {
        if(id.trim().isEmpty()){
            List<MovieModel> movieModels = movieRepository.findAll();
            model.addAttribute("trends",movieModels);
            return "trend";
        }
        if(!checkRegex(id.toLowerCase())){
            return "hacker";
        }
        String sql = MessageFormat.format("SELECT * FROM movies_trending WHERE id =''{0}'' ",id);
        Connection connection = jdbcConnect.getConnection();
        PreparedStatement statement = null;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        MovieModel movieModel = new MovieModel();
        while (resultSet.next()){
            movieModel.setId(resultSet.getLong("id"));
            movieModel.setDescription(resultSet.getString("description"));
            movieModel.setPath(resultSet.getString("path"));
            movieModel.setTitle(resultSet.getString("title"));
            movieModel.setDateTime(resultSet.getString("date_time"));
            movieModel.setPopularity(resultSet.getFloat("popularity"));
            movieModel.setType(resultSet.getString("type"));
        }
        model.addAttribute("movieDetail",movieModel);
        return "trendDetail";
    }
    @GetMapping(value = "/search")
    public String search(SearchDTO searchDTO){
        if(searchDTO.getSearch().trim().isEmpty()){
            return "redirect:/movies";
        }
        return "redirect:/movies?search="+searchDTO.getSearch();
    }
    public static Boolean checkRegex(String str){
        final String regex = "and|union|null|;";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            return false;
        }
        return true;
    }
    @PostMapping (value ="/login")
    public String login(LoginDTO loginDTO, Model model, HttpServletResponse httpServletResponse){
        User user = userRepository.findUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        if(user==null){
            model.addAttribute("user",new LoginDTO(loginDTO.getUsername(),loginDTO.getPassword()));
            model.addAttribute("error",true);
            return "login";
        }
        TrackingId trackingId = trackingRepository.findTrackingIdByIdUsername(user.getId());
        Cookie cookie = new Cookie("TrackingId",trackingId.getName());
        httpServletResponse.addCookie(cookie);
        return "redirect:/movies";
    }
//    @PostMapping (value ="/register")
//    public String register(RegisterDTO registerDTO,Model model){
//        User user = userRepository.findUserByUsername(registerDTO.getUsername());
//        if(user!=null){
//            model.addAttribute("user",new RegisterDTO(registerDTO.getUsername(),registerDTO.getPassword(),registerDTO.getRepeatPassword()));
//            model.addAttribute("usernameExist",true);
//            return "register";
//        }
//        if (registerDTO.getPassword().equals(registerDTO.getRepeatPassword())){
//            User userSave = new User();
//            userSave.setUsername(registerDTO.getUsername());
//            userSave.setPassword(registerDTO.getPassword());
//            userRepository.save(userSave);
//            String trackingId = generateStrackingId();
//            TrackingId trackingId1 = new TrackingId();
//            trackingId1.setIdUsername(userSave.getId());
//            trackingId1.setName(trackingId);
//            trackingRepository.save(trackingId1);
//            model.addAttribute("user",new LoginDTO());
//            return "index";
//        }
//        model.addAttribute("user",new RegisterDTO(registerDTO.getUsername(),registerDTO.getPassword(),registerDTO.getRepeatPassword()));
//        model.addAttribute("errorPassword",true);
//        return "register";
//    }

//    public static String generateStrackingId(){
//        int leftLimit = 97; // letter 'a'
//        int rightLimit = 122; // letter 'z'
//        int targetStringLength = 20;
//        Random random = new Random();
//        StringBuilder buffer = new StringBuilder(targetStringLength);
//        for (int i = 0; i < targetStringLength; i++) {
//            int randomLimitedInt = leftLimit + (int)
//                    (random.nextFloat() * (rightLimit - leftLimit + 1));
//            buffer.append((char) randomLimitedInt);
//        }
//        String generatedString = buffer.toString();
//        return generatedString;
//    }

}
