
function encrypt(word,_key){
    var key = CryptoJS.enc.Utf8.parse(_key || 'telecom_wap_2018');
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}