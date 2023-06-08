package com.tunahan.yazlab.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tunahan.yazlab.Exeptions.ResourceNotFoundException;
import com.tunahan.yazlab.Models.Dizi;
import com.tunahan.yazlab.Models.Kelime;
import com.tunahan.yazlab.Repositories.KelimeRepository;

@CrossOrigin
@RestController
public class HomeController {

    @Autowired
	private KelimeRepository repository;

    @RequestMapping("/")
    public String deneme(){
        return "Yazlab Projesi";
    }

    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
    
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
          int lastValue = i;
          for (int j = 0; j <= s2.length(); j++) {
            if (i == 0)
              costs[j] = j;
            else {
              if (j > 0) {
                int newValue = costs[j - 1];
                if (s1.charAt(i - 1) != s2.charAt(j - 1))
                  newValue = Math.min(Math.min(newValue, lastValue),
                      costs[j]) + 1;
                costs[j - 1] = lastValue;
                lastValue = newValue;
              }
            }
          }
          if (i > 0)
            costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
      }

    //------------------ Kelime İşlemleri--------------------------------//
    @PostMapping("/kelime")
	public ResponseEntity<Object> greeting(@RequestBody Dizi kelimeler) {
        
        Map<String, Object> map = new HashMap<String, Object>();

        //Birleşir Mi
        Boolean durum = false;

        String message = "";

        //Birleşen Kelime
        String birlesen = "";

        long startTime = System.nanoTime();
        // Kod Başla
        if(kelimeler.getDizi().isEmpty()){
            message = "Girilen Diziler Boş.";
            durum = false;
        }else if(kelimeler.getDizi().size() == 1){
            durum = false;
            message = "Birleştirilemez";
            birlesen = kelimeler.getDizi().get(0);
        }else{
            ArrayList<String> sentences = new ArrayList<String>();

            for(int i=1;i<kelimeler.getDizi().size();i++){
                String ilk = kelimeler.getDizi().get(i-1);
                String son = kelimeler.getDizi().get(i);

                if(i==1){
                    sentences.add(ilk);
                }

                if(son.length() == 0){
                    sentences.add(son);
                }else{
                    int ilkindex = -1;
                    for(int j=0;j<ilk.length();j++){
                        if(ilk.toLowerCase().charAt(j) == son.toLowerCase().charAt(0)){
                            if(son.length() >= ilk.length() - j){
                                if(ilk.toLowerCase().substring(j).equals(son.toLowerCase().substring(0, ilk.length() - j))){
                                    ilkindex =  ilk.length() - j;
                                    break;
                                }
                            }
                        }
                    }

                    if(ilkindex==-1){
                        sentences.add(son);
                    }else{
                        sentences.add(son.substring(ilkindex));
                    }
                }

            }

            for(int i=0;i<sentences.size();i++){
                birlesen += sentences.get(i);
            }

            message = "Birleşir";
            durum = true;
        } 

        // Kod Bitir
        long endTime   = System.nanoTime();
        
        long totalTime = endTime - startTime;

        map.put("message", message);
        map.put("status", 200);
        map.put("kelimeler", kelimeler);
        map.put("birlesen", birlesen);
        map.put("bool",durum);
        map.put("time",totalTime);

        
		return new ResponseEntity<Object>(map , HttpStatus.OK);
	}

    //------------------ Kelimeleri Döndürme--------------------------------//
    @GetMapping("/getkelimeler")
    public ResponseEntity<Object> getKelimeler() {

        List<Kelime> kelimeler = repository.findAll();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("status", 200);
        map.put("error", false);
        map.put("kelimeler", kelimeler);

		return new ResponseEntity<Object>(map , HttpStatus.OK);
	}

    //------------------ Id ile Kelime Döndürme --------------------------------//
    @GetMapping("/getkelimeler/{id}")
    public ResponseEntity<Object> getBook(@PathVariable(name = "id") Long employeeId) throws ResourceNotFoundException {

		Kelime kelime = repository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(employeeId + " Kelime Bulunamadı."));

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("status", 200);
        map.put("error", false);
        map.put("kelime", kelime);

        return new ResponseEntity<Object>(map , HttpStatus.OK);
	}

    //------------------ Kelime Kaydetme --------------------------------//
    @PostMapping("/setkelimeler")
	public ResponseEntity<Object> createEmployee(@RequestBody Kelime kelime) {

        LocalDateTime myObj = LocalDateTime.now();

        kelime.setCreatedAt(myObj);

        kelime.setUpdatedAt(myObj);

        repository.save(kelime);

        List<Kelime> kelimeler = repository.findAll();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("status", 200);
        map.put("error", false);
        map.put("kelime", kelime);
        map.put("kelimeler", kelimeler);
	
		return new ResponseEntity<Object>(map , HttpStatus.CREATED);
	}

    //------------------ Kelimeleri Silme --------------------------------//
    @PostMapping("deletekelimeler")
	public ResponseEntity<Object> deleteKelimeler() {

        repository.deleteAll();

        List<Kelime> kelimeler = repository.findAll();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("status", 200);
        map.put("error", false);
        map.put("kelimeler", kelimeler);

        return new ResponseEntity<Object>(map , HttpStatus.OK);
	}
}
