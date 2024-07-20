package com.application.friendhub.registrationProcess.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SelectOption {


   private String value;

   public SelectOption(String value) {
      this.value = value;
   }
}
