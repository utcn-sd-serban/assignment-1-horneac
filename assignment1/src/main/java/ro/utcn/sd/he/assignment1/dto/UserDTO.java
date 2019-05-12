package ro.utcn.sd.he.assignment1.dto;

import lombok.Data;
import ro.utcn.sd.he.assignment1.model.User;

@Data
public class UserDTO {
    private int id;
    private String userName;
    private String password;

    public static UserDTO ofEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUsername());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

}
