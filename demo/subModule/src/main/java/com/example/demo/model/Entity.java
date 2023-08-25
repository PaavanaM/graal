package com.example.demo.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a top level entity (can represent a group or user)
 */
@Validated
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
  @JsonProperty("id")
  private String id;

  @JsonProperty("uniquename")
  private String uniquename;

  @JsonProperty("entityType")
  private EntityType entityType;


  public enum EntityType {
    USER("USER"),

    GROUP("GROUP");

    private String value;

    EntityType(String value) {
      this.value = value;
    }

    @JsonCreator
    public static EntityType fromValue(String text) {
      for (EntityType b : EntityType.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }
  }

}

