package model.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Client {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
