package com.swg.beans;

import lombok.*;

import java.util.Set;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String subject;

    private String message;

    private String receivers;
}
