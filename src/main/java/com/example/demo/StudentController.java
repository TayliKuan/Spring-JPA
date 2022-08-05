package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostMapping("/students")
	public String insert(@RequestBody Student student) {
		studentRepository.save(student);
		return "jpa created~~~";
	}
	
	@PutMapping("/students/{studentId}")
	public String update(@PathVariable Integer studentId, @RequestBody Student student) {
		//先去資料庫看有沒有這筆資料
		Student s = studentRepository.findById(studentId).orElse(null);
		
		if(s != null) {
			//有這筆資料的話 只要把要改的欄位寫進來 其他維持不動
			s.setName(student.getName());
			studentRepository.save(s);
			return "jpa updated~~~";
		} else {
			return "updated fail no data~~~";
		}
		
	}
	
	@DeleteMapping("/students/{studentId}")
	public String delete(@PathVariable Integer studentId) {
		studentRepository.deleteById(studentId);
		return "jpa deleted~~~";
	}
	
	@GetMapping("/students/{studentId}")
	public Student read(@PathVariable Integer studentId) {
		Student student = studentRepository.findById(studentId).orElse(null);
		return student;
	}
	
	@PostMapping("/students/findByName")
	public List<Student> findByName(@RequestBody Student student) {
		//會自動映射 json to java Object 再拿出來用就好了
		List<Student> students = studentRepository.findByName(student.getName());
		return students;
	}
	
//	@PostMapping("/students/findByName")
//	public List<Student> findByName(@RequestBody String jsonStr) {
//		//https://www.baeldung.com/jackson-object-mapper-tutorial
//		//或是自己手動把json轉物件
//		Student s;
//		List<Student> students = new ArrayList<Student>();
//		try {
//			s = objectMapper.readValue(jsonStr, Student.class);
//			System.out.println(s.getName());
//			students = studentRepository.findByName(s.getName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//		return students;
//	}
	
//	@PostMapping("/students/findByName")
//	public List<Student> findByName(@RequestBody String jsonStr) {
//		//MAP 可以接 json (不建domain時用)
//		Map<String,String> s;
//		List<Student> students = new ArrayList<Student>();
//		try {
//			s = objectMapper.readValue(jsonStr, Map.class);
//			System.out.println(s.get("name"));
//			students = studentRepository.findByName(s.get("name"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return students;
//	}
	
	@PostMapping("/students/findByIdAndName")
	public Student findByIdAndName(@RequestBody Student student) {
		return studentRepository.findByIdAndName(student.getId(), student.getName());
	}
	
	@PostMapping("/students/testQuery")
	public Student testQuery(@RequestBody Student student) {
		return studentRepository.testQuery(student.getId(), student.getName());
	}
}
