/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models.amazon;

import com.config.Configs;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author duyuno
 */
public class ProductTypes {
    
    public static final int TYPE_PARENT_BOTH = 1;
    public static final int TYPE_PARENT_COLOR = 2;
    public static final int TYPE_PARENT_SIZE = 3;
    public static final int TYPE_CHILD_COLOR = 4;
    public static final int TYPE_CHILD_SIZE = 5;
    public static final int TYPE_CHILD_BOTH = 6;
    public static final int TYPE_NORMAL = 0;

    public static final String PRODUCT_TYPE_FILE = "/ProductTypes.txt";

    public static final String TYPE_AUTO_ACCESSORY = "AutoAccessory";
    public static final String TYPE_BABY = "Baby";
    public static final String TYPE_BEAUTY = "Beauty";
    public static final String TYPE_CE = "CE (Consumer Electronics)";
    public static final String TYPE_CAMERA_PHOTO = "CameraPhoto";
    public static final String TYPE_COMPUTER = "Computers";
    public static final String TYPE_FOOD_AND_BEVERAGES = "FoodAndBeverages";
    public static final String TYPE_FOOD_SERVICE_AND_JANSAN = "FoodServiceAndJanSan";
    public static final String TYPE_GIFT_CARDS = "GiftCards";
    public static final String TYPE_GOURMET = "Gourmet";
    public static final String TYPE_HEALTH = "Health";
    public static final String TYPE_HOME = "Home";
    public static final String TYPE_HOME_IMPROVEMENT = "HomeImprovement";
    public static final String TYPE_JEWELRY = "Jewelry";
    public static final String TYPE_LAB_SUPPLIES = "LabSupplies";
    public static final String TYPE_LARGE_APPLIANCES = "LargeAppliances";
    public static final String TYPE_LIGHTING = "Lighting";
    public static final String TYPE_MECHANICAL_FASTENERS = "MechanicalFasteners";
    public static final String TYPE_MISCELLANEOUS = "Miscellaneous";
    public static final String TYPE_MOTORCYCLES = "Motorcycles";
    public static final String TYPE_MUSIC = "Music";
    public static final String TYPE_MUSICAL_INSTRUMENTS = "MusicalInstruments";
    public static final String TYPE_OFFICE = "Office";
    public static final String TYPE_PET_SUPPLIES = "PetSupplies";
    public static final String TYPE_POWER_TRANSMISSION = "PowerTransmission";
    public static final String TYPE_RAW_MATERIALS = "RawMaterials";
    public static final String TYPE_SWVG = "SWVG";
    public static final String TYPE_SPORTS = "Sports";
    public static final String TYPE_TIRES_AND_WHEELS = "TiresAndWheels";
    public static final String TYPE_TOYS = "Toys";
    public static final String TYPE_TOYS_BABY = "ToysBaby";
    public static final String TYPE_VIDEO = "Video";
    public static final String TYPE_WIRELESS = "Wireless";
    public static final String TYPE_MISCELLANEOUS_SUB = "MiscellaneousSub";
    public static final String TYPE_CLOTHING_TYPE = "ClothingType";

    public static List<String> listAutoAccessory = new ArrayList<>();
    public static List<String> listBaby = new ArrayList<>();
    public static List<String> listBeauty = new ArrayList<>();
    public static List<String> listCE = new ArrayList<>();
    public static List<String> listCameraPhoto = new ArrayList<>();
    public static List<String> listComputers = new ArrayList<>();
    public static List<String> listFoodAndBeverages = new ArrayList<>();
    public static List<String> listFoodServiceAndJanSan = new ArrayList<>();
    public static List<String> listGiftCards = new ArrayList<>();
    public static List<String> listGourmet = new ArrayList<>();
    public static List<String> listHealth = new ArrayList<>();
    public static List<String> listHome = new ArrayList<>();
    public static List<String> listHomeImprovement = new ArrayList<>();
    public static List<String> listJewelry = new ArrayList<>();
    public static List<String> listLabSupplies = new ArrayList<>();
    public static List<String> listLargeAppliances = new ArrayList<>();
    public static List<String> listLighting = new ArrayList<>();
    public static List<String> listMechanicalFasteners = new ArrayList<>();
    public static List<String> listMiscellaneous = new ArrayList<>();
    public static List<String> listMotorcycles = new ArrayList<>();
    public static List<String> listMusic = new ArrayList<>();
    public static List<String> listMusicalInstruments = new ArrayList<>();
    public static List<String> listOffice = new ArrayList<>();
    public static List<String> listPetSupplies = new ArrayList<>();
    public static List<String> listPowerTransmission = new ArrayList<>();
    public static List<String> listRawMaterials = new ArrayList<>();
    public static List<String> listSWVG = new ArrayList<>();
    public static List<String> listSports = new ArrayList<>();
    public static List<String> listTiresAndWheels = new ArrayList<>();
    public static List<String> listToys = new ArrayList<>();
    public static List<String> listToysBaby = new ArrayList<>();
    public static List<String> listVideo = new ArrayList<>();
    public static List<String> listWireless = new ArrayList<>();
    public static List<String> listMiscellaneousSub = new ArrayList<>();
    public static List<String> listClothingType = new ArrayList<>();

    public static HashMap<String, List<String>> hashMapProduct = new HashMap<>();
    public static ArrayList<String> listAllTypes = new ArrayList<>();
    public static String[] arrayAllTypes = null;

    public static List<String> listCurrent = null;

    static {

        try {
//            File file = new File(PRODUCT_TYPE_FILE);
            
            InputStream inputStream = Configs.class.getResourceAsStream(PRODUCT_TYPE_FILE);
            InputStreamReader clientSecretReader = new InputStreamReader(inputStream);

//            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(clientSecretReader);

            String st;
            while ((st = br.readLine()) != null) {
                if (st != null && !st.isEmpty()) {
                    if (st.contains("[") && st.contains("]")) {
                        String type = st.substring(1, st.length() - 1);
                        switch (type) {
                            case TYPE_AUTO_ACCESSORY:
                                listCurrent = listAutoAccessory;
                                break;
                            case TYPE_BABY:
                                listCurrent = listBaby;
                                break;
                            case TYPE_BEAUTY:
                                listCurrent = listBeauty;
                                break;
                            case TYPE_CE:
                                listCurrent = listCE;
                                break;
                            case TYPE_CAMERA_PHOTO:
                                listCurrent = listCameraPhoto;
                                break;
                            case TYPE_COMPUTER:
                                listCurrent = listComputers;
                                break;
                            case TYPE_FOOD_AND_BEVERAGES:
                                listCurrent = listFoodAndBeverages;
                                break;
                            case TYPE_FOOD_SERVICE_AND_JANSAN:
                                listCurrent = listFoodServiceAndJanSan;
                                break;
                            case TYPE_GIFT_CARDS:
                                listCurrent = listGiftCards;
                                break;
                            case TYPE_GOURMET:
                                listCurrent = listGourmet;
                                break;
                            case TYPE_HEALTH:
                                listCurrent = listHealth;
                                break;
                            case TYPE_HOME:
                                listCurrent = listHome;
                                break;
                            case TYPE_HOME_IMPROVEMENT:
                                listCurrent = listHomeImprovement;
                                break;
                            case TYPE_JEWELRY:
                                listCurrent = listJewelry;
                                break;
                            case TYPE_LAB_SUPPLIES:
                                listCurrent = listLabSupplies;
                                break;
                            case TYPE_LARGE_APPLIANCES:
                                listCurrent = listLargeAppliances;
                                break;
                            case TYPE_LIGHTING:
                                listCurrent = listLighting;
                                break;
                            case TYPE_MECHANICAL_FASTENERS:
                                listCurrent = listMechanicalFasteners;
                                break;
                            case TYPE_MISCELLANEOUS:
                                listCurrent = listMiscellaneous;
                                break;
                            case TYPE_MOTORCYCLES:
                                listCurrent = listMotorcycles;
                                break;
                            case TYPE_MUSIC:
                                listCurrent = listMusic;
                                break;
                            case TYPE_MUSICAL_INSTRUMENTS:
                                listCurrent = listMusicalInstruments;
                                break;
                            case TYPE_OFFICE:
                                listCurrent = listOffice;
                                break;
                            case TYPE_PET_SUPPLIES:
                                listCurrent = listPetSupplies;
                                break;
                            case TYPE_POWER_TRANSMISSION:
                                listCurrent = listPowerTransmission;
                                break;
                            case TYPE_RAW_MATERIALS:
                                listCurrent = listRawMaterials;
                                break;
                            case TYPE_SWVG:
                                listCurrent = listSWVG;
                                break;
                            case TYPE_SPORTS:
                                listCurrent = listSports;
                                break;
                            case TYPE_TIRES_AND_WHEELS:
                                listCurrent = listTiresAndWheels;
                                break;
                            case TYPE_TOYS:
                                listCurrent = listToys;
                                break;
                            case TYPE_TOYS_BABY:
                                listCurrent = listToysBaby;
                                break;
                            case TYPE_VIDEO:
                                listCurrent = listVideo;
                                break;
                            case TYPE_WIRELESS:
                                listCurrent = listWireless;
                                break;
                            case TYPE_MISCELLANEOUS_SUB:
                                listCurrent = listMiscellaneousSub;
                                break;
                            case TYPE_CLOTHING_TYPE:
                                listCurrent = listClothingType;
                                break;
                        }
                    } else if (listCurrent != null) {
                        listCurrent.add(st);
                        listAllTypes.add(st);
                    }

                }
            }

            Collator usCollator = Collator.getInstance(Locale.US);
            usCollator.setStrength(Collator.PRIMARY); // ignores casing

            Collections.sort(listAllTypes, usCollator);
            arrayAllTypes = new String[listAllTypes.size()];

            for (int i = 0, size = listAllTypes.size(); i < size; i++) {
                arrayAllTypes[i] = listAllTypes.get(i);
            }

        } catch (Exception ex) {

        }

        hashMapProduct.put(TYPE_AUTO_ACCESSORY, listAutoAccessory);
        hashMapProduct.put(TYPE_BABY, listBaby);
        hashMapProduct.put(TYPE_BEAUTY, listBeauty);
        hashMapProduct.put(TYPE_CE, listCE);
        hashMapProduct.put(TYPE_CAMERA_PHOTO, listCameraPhoto);
        hashMapProduct.put(TYPE_COMPUTER, listComputers);
        hashMapProduct.put(TYPE_FOOD_AND_BEVERAGES, listFoodAndBeverages);
        hashMapProduct.put(TYPE_FOOD_SERVICE_AND_JANSAN, listFoodServiceAndJanSan);
        hashMapProduct.put(TYPE_GIFT_CARDS, listGiftCards);
        hashMapProduct.put(TYPE_GOURMET, listGourmet);
        hashMapProduct.put(TYPE_HEALTH, listHealth);
        hashMapProduct.put(TYPE_HOME, listHome);
        hashMapProduct.put(TYPE_HOME_IMPROVEMENT, listHomeImprovement);
        hashMapProduct.put(TYPE_JEWELRY, listJewelry);
        hashMapProduct.put(TYPE_LAB_SUPPLIES, listLabSupplies);
        hashMapProduct.put(TYPE_LARGE_APPLIANCES, listLargeAppliances);
        hashMapProduct.put(TYPE_LIGHTING, listLighting);
        hashMapProduct.put(TYPE_MECHANICAL_FASTENERS, listMechanicalFasteners);
        hashMapProduct.put(TYPE_MISCELLANEOUS, listMiscellaneous);
        hashMapProduct.put(TYPE_MOTORCYCLES, listMotorcycles);
        hashMapProduct.put(TYPE_MUSIC, listMusic);
        hashMapProduct.put(TYPE_MUSICAL_INSTRUMENTS, listMusicalInstruments);
        hashMapProduct.put(TYPE_OFFICE, listOffice);
        hashMapProduct.put(TYPE_PET_SUPPLIES, listPetSupplies);
        hashMapProduct.put(TYPE_POWER_TRANSMISSION, listPowerTransmission);
        hashMapProduct.put(TYPE_RAW_MATERIALS, listRawMaterials);
        hashMapProduct.put(TYPE_SWVG, listSWVG);
        hashMapProduct.put(TYPE_SPORTS, listSports);
        hashMapProduct.put(TYPE_TIRES_AND_WHEELS, listTiresAndWheels);
        hashMapProduct.put(TYPE_TOYS, listToys);
        hashMapProduct.put(TYPE_TOYS_BABY, listToysBaby);
        hashMapProduct.put(TYPE_VIDEO, listVideo);
        hashMapProduct.put(TYPE_WIRELESS, listWireless);
        hashMapProduct.put(TYPE_MISCELLANEOUS_SUB, listMiscellaneousSub);
        hashMapProduct.put(TYPE_CLOTHING_TYPE, listClothingType);
    }

    public static void initTypeata() {
        try {
//            File file = new File(PRODUCT_TYPE_FILE);
            
            InputStream inputStream = Configs.class.getResourceAsStream(PRODUCT_TYPE_FILE);
            InputStreamReader clientSecretReader = new InputStreamReader(inputStream);

//            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(clientSecretReader);

            String st;
            while ((st = br.readLine()) != null) {
                if (st != null && !st.isEmpty()) {
                    if (st.contains("[") && st.contains("]")) {
                        String type = st.substring(1, st.length() - 1);
                        switch (type) {
                            case TYPE_AUTO_ACCESSORY:
                                listCurrent = listAutoAccessory;
                                break;
                            case TYPE_BABY:
                                listCurrent = listBaby;
                                break;
                            case TYPE_BEAUTY:
                                listCurrent = listBeauty;
                                break;
                            case TYPE_CE:
                                listCurrent = listCE;
                                break;
                            case TYPE_CAMERA_PHOTO:
                                listCurrent = listCameraPhoto;
                                break;
                            case TYPE_COMPUTER:
                                listCurrent = listComputers;
                                break;
                            case TYPE_FOOD_AND_BEVERAGES:
                                listCurrent = listFoodAndBeverages;
                                break;
                            case TYPE_FOOD_SERVICE_AND_JANSAN:
                                listCurrent = listFoodServiceAndJanSan;
                                break;
                            case TYPE_GIFT_CARDS:
                                listCurrent = listGiftCards;
                                break;
                            case TYPE_GOURMET:
                                listCurrent = listGourmet;
                                break;
                            case TYPE_HEALTH:
                                listCurrent = listHealth;
                                break;
                            case TYPE_HOME:
                                listCurrent = listHome;
                                break;
                            case TYPE_HOME_IMPROVEMENT:
                                listCurrent = listHomeImprovement;
                                break;
                            case TYPE_JEWELRY:
                                listCurrent = listJewelry;
                                break;
                            case TYPE_LAB_SUPPLIES:
                                listCurrent = listLabSupplies;
                                break;
                            case TYPE_LARGE_APPLIANCES:
                                listCurrent = listLargeAppliances;
                                break;
                            case TYPE_LIGHTING:
                                listCurrent = listLighting;
                                break;
                            case TYPE_MECHANICAL_FASTENERS:
                                listCurrent = listMechanicalFasteners;
                                break;
                            case TYPE_MISCELLANEOUS:
                                listCurrent = listMiscellaneous;
                                break;
                            case TYPE_MOTORCYCLES:
                                listCurrent = listMotorcycles;
                                break;
                            case TYPE_MUSIC:
                                listCurrent = listMusic;
                                break;
                            case TYPE_MUSICAL_INSTRUMENTS:
                                listCurrent = listMusicalInstruments;
                                break;
                            case TYPE_OFFICE:
                                listCurrent = listOffice;
                                break;
                            case TYPE_PET_SUPPLIES:
                                listCurrent = listPetSupplies;
                                break;
                            case TYPE_POWER_TRANSMISSION:
                                listCurrent = listPowerTransmission;
                                break;
                            case TYPE_RAW_MATERIALS:
                                listCurrent = listRawMaterials;
                                break;
                            case TYPE_SWVG:
                                listCurrent = listSWVG;
                                break;
                            case TYPE_SPORTS:
                                listCurrent = listSports;
                                break;
                            case TYPE_TIRES_AND_WHEELS:
                                listCurrent = listTiresAndWheels;
                                break;
                            case TYPE_TOYS:
                                listCurrent = listToys;
                                break;
                            case TYPE_TOYS_BABY:
                                listCurrent = listToysBaby;
                                break;
                            case TYPE_VIDEO:
                                listCurrent = listVideo;
                                break;
                            case TYPE_WIRELESS:
                                listCurrent = listWireless;
                                break;
                            case TYPE_MISCELLANEOUS_SUB:
                                listCurrent = listMiscellaneousSub;
                                break;
                            case TYPE_CLOTHING_TYPE:
                                listCurrent = listClothingType;
                                break;
                        }
                    } else if (listCurrent != null) {
                        listCurrent.add(st);
                        listAllTypes.add(st);
                    }

                }
            }

            arrayAllTypes = new String[listAllTypes.size()];

            for (int i = 0, size = listAllTypes.size(); i < size; i++) {
                arrayAllTypes[i] = listAllTypes.get(i);
            }

        } catch (Exception ex) {

        }

        hashMapProduct.put(TYPE_AUTO_ACCESSORY, listAutoAccessory);
        hashMapProduct.put(TYPE_BABY, listBaby);
        hashMapProduct.put(TYPE_BEAUTY, listBeauty);
        hashMapProduct.put(TYPE_CE, listCE);
        hashMapProduct.put(TYPE_CAMERA_PHOTO, listCameraPhoto);
        hashMapProduct.put(TYPE_COMPUTER, listComputers);
        hashMapProduct.put(TYPE_FOOD_AND_BEVERAGES, listFoodAndBeverages);
        hashMapProduct.put(TYPE_FOOD_SERVICE_AND_JANSAN, listFoodServiceAndJanSan);
        hashMapProduct.put(TYPE_GIFT_CARDS, listGiftCards);
        hashMapProduct.put(TYPE_GOURMET, listGourmet);
        hashMapProduct.put(TYPE_HEALTH, listHealth);
        hashMapProduct.put(TYPE_HOME, listHome);
        hashMapProduct.put(TYPE_HOME_IMPROVEMENT, listHomeImprovement);
        hashMapProduct.put(TYPE_JEWELRY, listJewelry);
        hashMapProduct.put(TYPE_LAB_SUPPLIES, listLabSupplies);
        hashMapProduct.put(TYPE_LARGE_APPLIANCES, listLargeAppliances);
        hashMapProduct.put(TYPE_LIGHTING, listLighting);
        hashMapProduct.put(TYPE_MECHANICAL_FASTENERS, listMechanicalFasteners);
        hashMapProduct.put(TYPE_MISCELLANEOUS, listMiscellaneous);
        hashMapProduct.put(TYPE_MOTORCYCLES, listMotorcycles);
        hashMapProduct.put(TYPE_MUSIC, listMusic);
        hashMapProduct.put(TYPE_MUSICAL_INSTRUMENTS, listMusicalInstruments);
        hashMapProduct.put(TYPE_OFFICE, listOffice);
        hashMapProduct.put(TYPE_PET_SUPPLIES, listPetSupplies);
        hashMapProduct.put(TYPE_POWER_TRANSMISSION, listPowerTransmission);
        hashMapProduct.put(TYPE_RAW_MATERIALS, listRawMaterials);
        hashMapProduct.put(TYPE_SWVG, listSWVG);
        hashMapProduct.put(TYPE_SPORTS, listSports);
        hashMapProduct.put(TYPE_TIRES_AND_WHEELS, listTiresAndWheels);
        hashMapProduct.put(TYPE_TOYS, listToys);
        hashMapProduct.put(TYPE_TOYS_BABY, listToysBaby);
        hashMapProduct.put(TYPE_VIDEO, listVideo);
        hashMapProduct.put(TYPE_WIRELESS, listWireless);
        hashMapProduct.put(TYPE_MISCELLANEOUS_SUB, listMiscellaneousSub);
        hashMapProduct.put(TYPE_CLOTHING_TYPE, listClothingType);
    }
    
    public static int findProductTypeIndex(String productType) {
        if(productType == null || productType.isEmpty()) return -1;
        
        for(int i = 0, length = arrayAllTypes.length; i < length; i++) {
            String type = arrayAllTypes[i];
            if(type.toLowerCase().equals(productType.toLowerCase())) {
                return i;
            }
        }
        
        return -1;
    }
}
