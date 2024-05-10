package com.application.friendhub.registrationProcess.other;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SelectOption {


   private String value;

   public SelectOption(String value) {
      this.value = value;
   }
}
