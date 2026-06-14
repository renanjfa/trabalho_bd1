export default function InputCampo({icon: Icon, type, placeholder, value, onChange}){
    return(
        <div className="flex items-center w-full h-11 border border-gray-400 rounded-md bg-white px-4">
            <Icon size={19} className="text-black mr-4"/>
            <input type={type} placeholder={placeholder} value={value} onChange={(e)=> onChange(e.target.value)} className="w-full outline-none bg-transparent text-sm text-gray-700 placeholder:text-gray-400"/>
        </div>
    );
}