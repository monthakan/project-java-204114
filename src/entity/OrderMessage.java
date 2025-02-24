package entity;

import java.awt.Dimension;
import java.util.Random;
import javax.swing.JPanel;


public class OrderMessage extends JPanel{
    private static OrderMessage instance;
    private String currentMessage;
    private String currentWhat;
    private String currentHint;

    private Random random = new Random();

    private final String[] OrderList = {
        "ขอกะเพราหมูสับเห็ดเผ็ดๆ",
        "สั่งกะเพราหมูสับข้าวโพดไม่ใส่กะเพรา",
        "สั่งกะเพราหมูสับข้าวโพด",
        "เอากะเพราหมูสับเผ็ดๆ",
        "เอากะเพราหมูสับเผ็ดๆ ใส่หอมหัวใหญ่",
        "กะเพราปลาทูถั่วเขียว",
        "กะเพราเบคอนถั่วฝักยาว ไข่ดาว",
        "กะเพราหมูกรอบไข่เค็ม",
        "เอากะเพราไก่เต้าหู้",
        "สั่งกะเพราเนื้อปลาดุกฟู",
        "เอากะเพราเครื่องในไก่ไข่เจียว",
        "ซื้อกะเพราหมูสับเห็ด ไม่ใส่ใบกะเพรา",
        "เอากะเพราหมูกรอบถั่วรวม",
        "เอากะเพราหมูกรอบถั่วรวมไม่เผ็ด",
        "เอากะเพราหมูกรอบถั่วรวม ไม่เผ็ด ใส่หัวหอม ไม่ใส่กะเพรา",
        "เอากะเพราหมูสับปลาทู",
        "กะเพราหมูสับปลาทูไข่เค็ม",
        "กะเพราหมูสับปลาทู ถั่วฝักยาวไข่ดาว",
        "กะเพราหมูสับไข่ดาว",
        "กะเพราหมูสับเผ็ดไข่ดาว",
        "กะเพราไก่เบคอน",
        "กะเพราไก่เบคอนไข่ดาว",
        "กะเพราไก่เบคอนไข่ดาวถั่วฝักยาว",
        "กะเพราไก่เบคอนไข่ดาว ถั่วฝักยาวปลาทู",
        "กะเพราไก่เบคอนไข่ดาว ถั่วฝักยาวปลาทูเผ็ด",
        "กะเพราไก่เบคอนไข่ดาว ถั่วฝักยาวปลาทูเผ็ด ไม่ใส่กะเพรา",
        "กะเพราไก่เบคอนไข่ดาว ถั่วฝักยาวปลาทูเผ็ด ไม่ใส่กะเพรา ไม่ใส่หอมใหญ่",
        "กะเพราหมูกรอบข้าวโพดไม่ใส่กะเพรา",
        "กะเพราหมูกรอบข้าวโพด",
        "กะเพราหมูกรอบข้าวโพดถั่วฝักยาวไข่ดาว",
        "กะเพราหมูกรอบไข่ดาว",
        "กะเพราหมูสับเครื่องในไก่",
        "กะเพราหมูสับเครื่องในไก่ไข่เจียว",
        "กะเพราหมูสับเครื่องในไก่ไข่ดาว ไม่ใส่กะเพรา",
        "กะเพราหมูสับเครื่องในไก่ไข่ดาว",
        "กะเพราหมูสับเครื่องในไก่ ใส่ถั่วฝักยาว ข้าวโพด ไม่ใส่กะเทียม ไข่ดาว",
        "กะเพราหมูสับเครื่องในไก่ ใส่ถั่วฝักยาว ข้าวโพด ไข่ดาว",
        "กะเพราหมูสับเครื่องในไก่ ใส่ถั่วฝักยาว ข้าวโพด ไข่ดาว ไม่ใส่กะเพรา",
        "กะเพราเนื้อไข่ดาว",
        "กะเพราเนื้อไข่ดาว ไม่ใส่กะเพรา",
        "กะเพราเนื้อไข่ดาว ไม่ใส่กะเพรา ไม่ใส่หอมใหญ่",
        "กะเพราเนื้อไข่ดาว ไม่ใส่กะเพรา ไม่ใส่หอมใหญ่ ใส่ถั่วฝักยาว ข้าวโพด",
        "กะเพราเนื้อไข่ดาว ไม่ใส่กะเพรา ไม่ใส่หอมใหญ่ ใส่ถั่วฝักยาว ข้าวโพด ไข่ดาว",
        "กะเพราเนื้อไข่ดาว ไม่ใส่กะเพรา ไม่ใส่หอมใหญ่ ใส่ถั่วฝักยาว ข้าวโพด ไข่ดาว ไม่ใส่กระเทียม",
        "กะเพราเนื้อไข่ดาวเผ็ดๆไข่ดาวไข่เจียว",
        "กะเเพราเนื้อเผ็ดๆ ไข่ดาวไข่เจียว ไม่ใส่กะเพรา",
        "กะเพราเนื้อไข่ดาวเผ็ดๆ ไข่เค็มไม่ใส่กะเพรา",
        "กะเพราเนื้อไข่ดาวเผ็ดๆ ไข่เค็มไม่ใส่กะเพรา ใส่หอมใหญ่",
        "กะเพราเนื้อเห็ดไข่ดาว",
        "กะเพราเนื้อเห็ดไข่เจียว",
        "กะเพราเนื้อเห็ดไข่เจียว ไม่ใส่กะเพรา",
        "กะเพราเนื้อไข่เจียว",
        "กะเพราเนื้อไข่เค็ม",
        "กะเพราเนื้อปลาทู",
        "กะเพราเนื้อเต้าหู้",
        "กะเพราเนื้อเต้าหู้ถั่วเขียวไข่เจียว",
        "กะเพราเนื้อปลาดุกฟูไข่ดาว",
        "กะเพราปลาทูไข่ดาว",
        "กะเพราปลาทูไข่เจียว",
        "กะเพราปลาทูไข่เจียว ไม่ใส่กะเพรา",
        "กะเพราปลาทูเห็ดรวมไข่ดาว",
        "กะเพราปลาทูเห็ดรวม ข้าวโพดไข่ดาว",
        "กะเพราเห็ดรวมข้าวโพดไข่ดาว",
        "กะเพราเห็ดรวมไข่เจียวไข่ดาว",
        "กะเพราเห็ดรวมไข่เจียวไข่ดาว ไม่ใส่กะเพรา",
        "กะเพราเต้าหู้ถั่วเขียวไข่เจียว",
        "กะเพราเต้าหู้ถั่วเขียวไข่เจียว ไม่ใส่กะเพรา",
        "กะเพราเจ",
        "กะเพรารวมไข่",
        "กะเพราปลาดุกฟูไข่ดาว",
    };
    private final String[] OrderHint = {
        "ใส่หมูสับ,เห็ด,ใบกะเพรา,พริกแดง,พริกแห้ง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ข้าวโพด,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ข้าวโพด,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ใบกะเพรา,พริกแดง,พริกแห้ง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ใบกะเพรา,พริกแดง,พริกแห้ง ,หอมใหญ่,กระเทียมและซอสกะเพรา",
        "ใส่ปลาทู,ถั่วเขียว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เบคอน,ไข่ดาว,ถั่วฝักยาว,ใบกะเพรา ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่หมูกรอบ,ไข่เค็ม,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,เต้าหู้,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อปลาดุกฟู,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เครื่องในไก่,ไข่เจียว,ใบกะเพรา, พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,เห็ด,พริกแดง,พริกแห้ง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูกรอบ,ถั่วรวม,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูกรอบ,ถั่วรวม,ใบกะเพรา ,กระเทียมและซอสกะเพรา",
        "ใส่หมูกรอบ,ถั่วรวม,หัวหอม ,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ปลาทู,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ปลาทู,ไข่เค็ม,ใบกะเพรา ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ปลาทู,ถั่วฝักยาว,ไข่ดาว,ใบกะเพรา ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ไข่ดาว,ใบกะเพรา,พริกแดง ,พริกแห้ง,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,เบคอน,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,เบคอน,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,เบคอน,ปลาทู,ไข่ดาว,ถั่วฝักยาว,ใบกะเพรา ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,เบคอน,ไข่ดาว,ถั่วฝักยาว ,ใบกะเพรา,พริกแดง ,พริกแห้ง,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,เบคอน,ไข่ดาว,ถั่วฝักยาว ,ปลาทู,พริกแดง,พริกแห้ง ,ใบกะเพรา,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,เบคอน,ไข่ดาว,ถั่วฝักยาว ,ปลาทู,พริกแดง,พริกแห้ง ,หอมใหญ่,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,เบคอน,ไข่ดาว,ถั่วฝักยาว ,ปลาทู,พริกแดง ,พริกแห้ง,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,หมูกรอบ,พริกแดง,กระเทียมและซอสกะเพรา ,ไม่ใส่กะเพรา",
        "ใส่ไก่,หมูกรอบ,ถั่วฝักยาว,ไข่ดาว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ไก่,หมูกรอบ,ไข่ดาว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เครื่องในไก่,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เครื่องในไก่,ไข่เจียว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เครื่องในไก่,ไข่ดาว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เครื่องในไก่,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,เครื่องในไก่,ถั่วฝักยาว,ข้าวโพด,ใบกะเพรา ,ไข่ดาวและซอสกะเพรา",
        "ใส่หมูสับ,เครื่องในไก่,ถั่วฝักยาว,ข้าวโพด,ใบกะเพรา ,ไข่ดาว,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,เครื่องในไก่,ถั่วฝักยาว, ข้าวโพด,ไข่ดาว,กระเทียมและซอสกะเพรา",
        "ใส่หมูสับ,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่ดาว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่ดาว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่ดาว,ถั่วฝักยาว,ข้าวโพด ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่ดาว,ถั่วฝักยาว,ข้าวโพด ,ไข่ดาว,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่ดาว,ถั่วฝักยาว,ข้าวโพด ,ไข่ดาวและซอสกะเพรา",
        "ใส่เนื้อ,ไข่เจียว,ไข่ดาว,ใบกะเพรา ,พริกแดง,พริกแห้งกระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่เจียว,ไข่ดาว,พริกแดง ,พริกแห้งกระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่เจียว,ไข่ดาว,พริกแดง ,พริกแห้งกระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่เค็ม,ไข่ดาว,พริกแดง ,พริกแห้ง,หอมใหญ่,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,เห็ด,ไข่ดาว,ใบกะเพรา ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,เห็ด,ไข่ดาว,ใบกะเพรา ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,เห็ด,ไข่เจียว,พริกแดง, กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,เห็ด,ไข่เจียว,พริกแดง, กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่เจียว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ไข่เค็ม,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ปลาทู,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,เต้าหู้,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,เต้าหู้,ถั่วเขียว,ไข่เจียว,ใบกะเพรา ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่เนื้อ,ปลาดุกฟู,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ปลาทู,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ปลาทู,ไข่เจียว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ปลาทู,ไข่เจียว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ปลาทู,เห็ดรวม,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ปลาทู,เห็ดรวม,ข้าวโพด,ไข่ดาว,ใบกะเพรา ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่เห็ดรวม,ข้าวโพด,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เห็ดรวม,ไข่เจียว,ไข่ดาว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เห็ดรวม,ไข่เจียว,ไข่ดาว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เต้าหู้,ถั่วเขียว,ไข่เจียว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่เต้าหู้,ถั่วเขียว,ไข่เจียว ,พริกแดง,กระเทียมและซอสกะเพรา",
        "ใส่เห็ด,ข้าวโพด,ถั่วเขียว,ถั่วเหลือง,ถั่วฝักยาว,พริกแดง ,กระเทียมและซอสกะเพรา",
        "ใส่ไข่ดาว,ไข่เจียว,ไข่เค็ม,พริกแดง,กระเทียม ,ซอสกะเพรา,ใบกะเพรา",
        "ใส่ปลาดุกฟู,ไข่ดาว,ใบกะเพรา,พริกแดง ,กระเทียมและซอสกะเพรา",
    };
    private final String[] OrderWhat = {
        "ใส่พริกแห้ง",
        "ไม่ใส่กะเพรา",
        "ใส่ข้าวโพด",
        "ใส่พริกแห้ง",
        "ใส่หอมใหญ่",
        "ใส่ถั่วเขียวและปลาทู",
        "ใส่เบคอน,ไข่ดาว,ถั่วฝักยาว",
        "ใส่ไข่เค็ม",
        "ใส่ไก่,เต้าหู้",
        "ใส่ปลาดุกฟู",
        "ใส่เครื่องในไก่และไข่เจียว",
        "เห็ดไม่ใส่ใบกะเพรา",
        "ใส่ถั่วรวม",
        "ไม่เผ็ดและใส่ถั่ว",
        "ไม่เผ็ด,ใส่หัวหอม,ใส่ถั่ว,ไม่ใส่ใบกะเพรา",
        "ใส่ปลาทู",
        "ใส่ไข่เค็มและปลาทู",
        "ใส่ถั่วฝักยาว,ไข่ดาว",
        "ใส่ไข่ดาว",
        "ใส่พริกแห้งและไข่ดาว",
        "ใส่เบคอน",
        "ใส่เบคอน,ไข่ดาว",
        "ใส่เบคอน,ไข่ดาว,ถั่วฝักยาว",
        "ใส่เบคอน,ไข่ดาว,ถั่วฝักยาว,ปลาทู",
        "ใส่เบคอน,ไข่ดาว,ถั่วฝักยาว,ปลาทู,พริกแห้ง",
        "ใส่ปลาทู,พริกแห้ง,ไม่ใส่กะเพรา",
        "ใส่ถั่วฝักยาว,พริกแห้ง,ไม่ใส่หอม,ไม่ใช่กะเพรา,ไข่ดาว",
        "ใส่ข้าวโพดไม่ใส่กะเพรา",
        "ข้าวโพด",
        "ข้าวโพดถั่วฝักยาวไข่ดาว",
        "ใส่ไข่ดาว",
        "ใส่เครื่องในไก่",
        "ใส่เครื่องในไก่,ไข่เจียว",
        "ใส่ไข่ดาว,ไม่ใส่กะเพรา",
        "ใส่ไข่ดาว",
        "ไม่ใส่กะเทียม",
        "ใส่ถั่วฝักยาว,ข้าวโพด และพิเศษไข่ดาว",
        "ไม่ใส่กะเพรา",
        "ใส่ไข่ดาว",
        "ไม่ใส่กะเพรา",
        "ไม่ใส่กะเพรา ไม่ใส่หอมใหญ่",
        "ใส่ถั่วฝักยาว,ข้าวโพด",
        "ใส่ถั่วฝักยาว,ข้าวโพด,ไข่ดาว",
        "ใส่ถั่วฝักยาว,ข้าวโพด,ไข่ดาว",
        "ใส่ถั่วฝักยาว,ข้าวโพด,ไข่เจียว,พริกแห้ง",
        "ใส่ไข่เจียว,ไข่ดาว,ถั่วฝักยาว,ข้าวโพด",
        "ไข่เค็มกับไข่ดาว",
        "ไข่เค็มกับไข่ดาว ไม่ใส่กะเพรา",
        "ไข่เค็มกับไข่ดาว ไม่ใส่กะเพรา ใส่หอมใหญ่",
        "เห็ดไข่ดาว",
        "เห็ดไข่เจียว ไม่ใส่กะเพรา",
        "เห็ดไข่เจียว ไม่ใส่กะเพรา",
        "เนื้อไข่เจียว",
        "เนื้อไข่เค็ม",
        "ปลาทู",
        "เต้าหู้ถั่วเขียวไข่เจียว",
        "ปลาดุกฟูไข่ดาว",
        "ปลาทูไข่ดาว",
        "ปลาทูไข่เจียว ไม่ใส่กะเพรา",
        "ปลาทูไข่เจียว ไม่ใส่กะเพรา",
        "ปลาทูเห็ดรวมไข่ดาว",
        "ปลาทูเห็ดรวมข้าวโพดไข่ดาว",
        "เห็ดรวมข้าวโพดไข่ดาว",
        "เห็ดรวมไข่เจียวไข่ดาวไข่เจียว",
        "เห็ดรวมไข่เจียวไข่ดาว ไม่ใส่กะเพรา",
        "เต้าหู้ถั่วเขียวไข่เจียว",
        "เต้าหู้ถั่วเขียวไข่เจียว และไม่ใส่ใบกะเพรา",
        "ผักทุกชนิด",
        "รวมไข่",
        "ปลาดุกฟูไข่ดาว"
    };
    private OrderMessage() {
        setPreferredSize(new Dimension(400, 100));
        showRandomOrder(); // เรียกใช้เมื่อเริ่มต้น
    }

    public static OrderMessage getInstance() {
        if (instance == null) {
            instance = new OrderMessage();
        }
        return instance;
    }

    public void showRandomOrder() {
        Random rand = new Random();
        int index = rand.nextInt(OrderList.length);
        

        currentMessage = OrderList[index];
        currentWhat = OrderWhat[index];
        currentHint = OrderHint[index];
        repaint();
    }

    public String getMessage() {
        return currentMessage;
    }

    public String getWhat() {
        return currentWhat;
    }
    public String getHint() {
        return currentHint;
    }

    
}