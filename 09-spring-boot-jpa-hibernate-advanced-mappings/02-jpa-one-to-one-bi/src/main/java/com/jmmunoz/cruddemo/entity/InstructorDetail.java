package com.jmmunoz.cruddemo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// annotate the class as an entity and map to db table
@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {
  
  // define the fields
  // annotate the fields with db column names
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  
  @Column(name = "youtube_channel")
  private String youtubeChannel;
  
  @Column(name = "hobby")
  private String hobby;

  // add @OneToOne annotation (bi-directional)
  // El nombre que aparece en mappedBy tiene que ser el mismo que tenemos en la entity Instructor, 
  // al campo que referencia a InstructorDetail.
  @OneToOne(mappedBy = "instructorDetail", cascade = CascadeType.ALL)
  private Instructor instructor;

  // create constructors
  public InstructorDetail() {
  }

  public InstructorDetail(String youtubeChannel, String hobby) {
    this.youtubeChannel = youtubeChannel;
    this.hobby = hobby;
  }
  
  // generate getter/setter methods
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getYoutubeChannel() {
    return youtubeChannel;
  }

  public void setYoutubeChannel(String youtubeChannel) {
    this.youtubeChannel = youtubeChannel;
  }

  public String getHobby() {
    return hobby;
  }

  public void setHobby(String hobby) {
    this.hobby = hobby;
  }

  public Instructor getInstructor() {
    return instructor;
  }

  public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
  }

  // generate toString() method
  @Override
  public String toString() {
    // No indicamos aquí que recupere la info de instructor para evitar un bucle infinito.
    // Esto es porque iría a instructor y ahí escribiría de nuevo la información de instructorDetail,
    // que a su vez escribiría de nuevo instructor, y a su vez instructorDetail...
    return "InstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
  }

}
