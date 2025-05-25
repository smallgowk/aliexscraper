/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.amazon;

import com.config.Configs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AmzContentFormat {

    public static final String DES_FORMAT_FILE = "/description_form.txt";

    public static final String TITLE_KEY = "{tittle}";
    public static final String BRANDNAME_KEY = "{brandname}";
    public static final String MAIN_KEYWORD_KEY = "{mainkeyword}";
//    public static final String GROUP_TYPE_KEY = "{groupType}";
    public static final String KEY_PRODUCT_KEY = "{keyProduct}";
    public static final String SPECIFIC_KEY = "{Productspecific}";
    public static final String DESCRIPTION_KEY = "{Productdescription}";
    public static final String TIPS_KEY = "{Tips}";
    public static final String BULLET_KEY = "{bullet}";
    public static final String REASON_KEY = "{reason}";
    
    public static final String SEARCH_TERM_1 = "{searchterm1}";
    public static final String SEARCH_TERM_2 = "{searchterm2}";
    public static final String SEARCH_TERM_3 = "{searchterm3}";
    public static final String SEARCH_TERM_4 = "{searchterm4}";
    public static final String SEARCH_TERM_5 = "{searchterm5}";
    public static String descriptionDefaultForm = null;

    static {
        try {
            InputStream inputStream = Configs.class.getResourceAsStream(DES_FORMAT_FILE);
            InputStreamReader clientSecretReader = new InputStreamReader(inputStream);

//            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(clientSecretReader);
            String st;
            StringBuilder sb = new StringBuilder();
            while ((st = br.readLine()) != null) {
                sb.append(st.trim()).append("\n");
            }
            
            descriptionDefaultForm = sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(AmzContentFormat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
