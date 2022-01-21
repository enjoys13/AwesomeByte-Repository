//package co.id.jss.jssrestapi.common;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class VarValidation {
//
////    private final Logger LOG = LoggerFactory.getLogger(VarValidation.class);
//
//    private final CommonUtils commonUtils;
//
//    @Autowired
//    public VarValidation(CommonUtils commonUtils) {
//        this.commonUtils = commonUtils;
//    }
//
//    public ForexCurrency forexCurrencyExist(Optional<ForexCurrency> forexCurrency) throws VarException {
//
//        if(!forexCurrency.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_CURRENCY_NOT_EXIST);
//        }
//
//        return forexCurrency.get();
//    }
//
//    public void forexCurrencyNotExist(Optional<ForexCurrency> forexCurrency) throws VarException {
//
//        if (forexCurrency.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_CURRENCY_EXIST);
//        }
//    }
//
//    public ForexCurrencyPair forexCurrencyPairExist(Optional<ForexCurrencyPair> forexCurrencyPair) throws VarException {
//
//        if(!forexCurrencyPair.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_CURRENCY_PAIR_NOT_EXIST);
//        }
//        return forexCurrencyPair.get();
//    }
//
//    public void forexCurrencyPairNotExist(Optional<ForexCurrencyPair> forexCurrencyPair) throws VarException {
//
//        if (forexCurrencyPair.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.WARN_CURRENCY_PAIR_EXIST);
//        }
//    }
//
//    public ForexOpenPosition forexOpenPositionExist(Optional<ForexOpenPosition> forexOpenPosition) throws VarException {
//
//        if(!forexOpenPosition.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_OPEN_POSITION_NOT_EXIST);
//        }
//        return forexOpenPosition.get();
//    }
//
//    public void forexOpenPositionNotExist(Optional<ForexOpenPosition> forexOpenPosition) throws VarException {
//
//        if (forexOpenPosition.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.WARN_OPEN_POSITION_EXIST);
//        }
//    }
//
//    public ForexClosingRate forexClosingRateExist(Optional<ForexClosingRate> forexClosingRate) throws VarException {
//
//        if(!forexClosingRate.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_CLOSING_RATE_NOT_EXIST);
//        }
//        return forexClosingRate.get();
//    }
//
//    public void forexClosingRateNotExist(Optional<ForexClosingRate> forexClosingRate) throws VarException {
//
//        if (forexClosingRate.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.WARN_CLOSING_RATE_EXIST);
//        }
//    }
//
//    public BondData bondDataExist(Optional<BondData> bondData) throws VarException {
//
//        if(!bondData.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_BOND_DATA_NOT_EXIST);
//        }
//
//        return bondData.get();
//    }
//
//    public void bondDataNotExist(Optional<BondData> bondData) throws VarException {
//
//        if (bondData.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.WARN_BOND_DATA_EXIST);
//        }
//    }
//
//    public BondOpenPosition bondOpenPositionExist(Optional<BondOpenPosition> bondOpenPosition) throws VarException {
//
//        if(!bondOpenPosition.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_BOND_OPEN_POSITION_NOT_EXIST);
//        }
//
//        return bondOpenPosition.get();
//    }
//
//    public void bondOpenPositionNotExist(Optional<BondOpenPosition> bondOpenPosition) throws VarException {
//
//        if (bondOpenPosition.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.WARN_BOND_OPEN_POSITION_EXIST);
//        }
//    }
//
//    public BondClosingPosition bondClosingPositionExist(Optional<BondClosingPosition> bondClosingPosition) throws VarException {
//
//        if(!bondClosingPosition.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_BOND_CLOSING_POSITION_NOT_EXIST);
//        }
//
//        return bondClosingPosition.get();
//    }
//
//    public void bondClosingPositionNotExist(Optional<BondClosingPosition> bondClosingPosition) throws VarException {
//
//        if (bondClosingPosition.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.WARN_BOND_CLOSING_POSITION_EXIST);
//        }
//    }
//
//    public void differenceValueOfCurrency(String base, String quote) throws VarException{
//
//        if(base.equals(quote)){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_SAME_VALUE_CURRENCY
//            );
//        }
//    }
//
//    private void throwIfAnyVariablesNull(List<String> varNull) throws VarException {
//        if(!varNull.isEmpty()){
//            throw new VarException(HttpStatus.BAD_REQUEST, Constants.ERR_NULL_VARIABLES + varNull.toString());
//        }
//    }
//
//    public void checkNullForexCurrencyDTO(ForexCurrencyDTO forexCurrencyDTO) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
//        if(commonUtils.isNull(forexCurrencyDTO.getId())){
//            varNull.add("Short Name (ID)");
//        }
//        if(commonUtils.isNull(forexCurrencyDTO.getName())){
//            varNull.add("Full Name");
//        }
//        if(commonUtils.isNull(forexCurrencyDTO.isActive())){
//            varNull.add("Is Active");
//        }
//
//        throwIfAnyVariablesNull(varNull);
//    }
//
//    public List<String> checkNullForexCurrencyPairDTO(ForexCurrencyPairDTO forexCurrencyPairDTO, boolean doThrows) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
//        if(commonUtils.isNull(forexCurrencyPairDTO.getBaseCurrency())){
//            varNull.add("Base Currency");
//        }
//        if(commonUtils.isNull(forexCurrencyPairDTO.getQuoteCurrency())){
//            varNull.add("Quote Currency");
//        }
//        if(commonUtils.isNull(forexCurrencyPairDTO.isActive())){
//            varNull.add("Is Active");
//        }
//        if(forexCurrencyPairDTO.getSourceData()!= null && commonUtils.isNull(forexCurrencyPairDTO.getSourceData().getSourceId())){
//            varNull.add("Source Data");
//        }
//
//        if(doThrows){
//            throwIfAnyVariablesNull(varNull);
//        }
//
//        return varNull;
//    }
//
//    public List<String> checkNullForexOpenPositionDTO(ForexOpenPositionDTO forexOpenPositionDTO, boolean doThrows) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
//        if(commonUtils.isNull(forexOpenPositionDTO.getDate())){
//            varNull.add("Date");
//        }
//        if(commonUtils.isNull(forexOpenPositionDTO.getBaseCurrency())){
//            varNull.add("Base Currency");
//        }
//        if(commonUtils.isNull(forexOpenPositionDTO.getQuoteCurrency())){
//            varNull.add("Quote Currency");
//        }
//        if(commonUtils.isNull(forexOpenPositionDTO.getAmount())){
//            varNull.add("Amount");
//        }
//        if(commonUtils.isNull(forexOpenPositionDTO.getRateOpen())){
//            varNull.add("Rate Open");
//        }
//        if(forexOpenPositionDTO.getSourceData()!= null && commonUtils.isNull(forexOpenPositionDTO.getSourceData().getSourceId())){
//            varNull.add("Source Data");
//        }
//
//        if(doThrows){
//            throwIfAnyVariablesNull(varNull);
//        }
//
//        return varNull;
//    }
//
//    public List<String> checkNullForexClosingRateDTO(ForexClosingRateDTO forexClosingRateDTO, boolean doThrows) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
//        if(commonUtils.isNull(forexClosingRateDTO.getDate())){
//            varNull.add("Date");
//        }
//        if(commonUtils.isNull(forexClosingRateDTO.getBaseCurrency())){
//            varNull.add("Base Currency");
//        }
//        if(commonUtils.isNull(forexClosingRateDTO.getQuoteCurrency())){
//            varNull.add("Quote Currency");
//        }
//        if(commonUtils.isNull(forexClosingRateDTO.getRateClose())){
//            varNull.add("Rate Close");
//        }
//        if(forexClosingRateDTO.getSourceData()!= null && commonUtils.isNull(forexClosingRateDTO.getSourceData().getSourceId())){
//            varNull.add("Source Data");
//        }
//
//        if(doThrows){
//            throwIfAnyVariablesNull(varNull);
//        }
//
//        return varNull;
//    }
//
//    public List<String> checkNullBondDataDTO(BondDataDTO bondDataDTO, boolean doThrows) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
////        if(commonUtils.isNull(bondDataDTO.getId())){
////            varNull.add("Id");
////        }
//        if(commonUtils.isNull(bondDataDTO.getName())){
//            varNull.add("Name");
//        }
//        if(commonUtils.isNull(bondDataDTO.getShortName())){
//            varNull.add("Short Name");
//        }
//        if(commonUtils.isNull(bondDataDTO.getCurrency())){
//            varNull.add("Currency");
//        }
//        if(commonUtils.isNull(bondDataDTO.isActive())){
//            varNull.add("Is Active");
//        }
//        if(bondDataDTO.getSourceData()!= null && commonUtils.isNull(bondDataDTO.getSourceData().getSourceId())){
//            varNull.add("Source Data");
//        }
//
//        if(doThrows){
//            throwIfAnyVariablesNull(varNull);
//        }
//
//        return varNull;
//    }
//
//    public List<String> checkNullBondOpenPositionDTO(BondOpenPositionDTO bondOpenPositionDTO, boolean doThrows) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
//        if(commonUtils.isNull(bondOpenPositionDTO.getDate())){
//            varNull.add("Date");
//        }
//        if(commonUtils.isNull(bondOpenPositionDTO.getLastNominal())){
//            varNull.add("Sell Nominal");
//        }
//        if(commonUtils.isNull(bondOpenPositionDTO.getLastPrice())){
//            varNull.add("Sell Price");
//        }
//        if(bondOpenPositionDTO.getSourceData()!= null && commonUtils.isNull(bondOpenPositionDTO.getSourceData().getSourceId())){
//            varNull.add("Source Data");
//        }
//
//        if(doThrows){
//
//            if(bondOpenPositionDTO.getBondData()!=null && commonUtils.isNull(bondOpenPositionDTO.getBondData().getId())){
//                varNull.add("Bond Id");
//            }
//
//            throwIfAnyVariablesNull(varNull);
//        }
//        else if(bondOpenPositionDTO.getBondData()!=null && commonUtils.isNull(bondOpenPositionDTO.getBondData().getIsinCode())){
//                varNull.add("Isin Code");
//        }
//
//        return varNull;
//    }
//
//    public List<String> checkNullBondClosingPositionDTO(BondClosingPositionDTO bondClosingPositionDTO, boolean doThrows) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
//        if(commonUtils.isNull(bondClosingPositionDTO.getDate())){
//            varNull.add("Date");
//        }
//        if(commonUtils.isNull(bondClosingPositionDTO.getRateClose())){
//            varNull.add("Rate Close");
//        }
//        if(bondClosingPositionDTO.getSourceData()!= null && commonUtils.isNull(bondClosingPositionDTO.getSourceData().getSourceId())){
//            varNull.add("Source Data");
//        }
//
//        if(doThrows){
//
//            if(bondClosingPositionDTO.getBondData()!=null && commonUtils.isNull(bondClosingPositionDTO.getBondData().getId())){
//                varNull.add("Bond Id");
//            }
//
//            throwIfAnyVariablesNull(varNull);
//        }
//        else if(bondClosingPositionDTO.getBondData()!=null && commonUtils.isNull(bondClosingPositionDTO.getBondData().getIsinCode())){
//            varNull.add("Isin Code");
//        }
//
//        return varNull;
//    }
//
//    public void fileIsNotNull(MultipartFile file) throws VarException {
//
//        if(file==null){
//
//            throw new VarException(HttpStatus.BAD_REQUEST, Constants.ERR_FILE_IS_NULL);
//        }
//    }
//
//    public void checkNullHolidayDTO(HolidayDTO holidayDTO) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
////        if(commonUtils.isNull(holidayDTO.getId())){
////            varNull.add("ID");
////        }
//        if(commonUtils.isNull(holidayDTO.getHolidayDate())){
//            varNull.add("Date");
//        }
//        if(commonUtils.isNull(holidayDTO.getDescription())){
//            varNull.add("Description");
//        }
//        if(commonUtils.isNull(holidayDTO.isActive())){
//            varNull.add("Is Active");
//        }
//
//        throwIfAnyVariablesNull(varNull);
//    }
//
//    public void holidayParameterNotExist(Optional<Holiday> holiday) throws VarException {
//
//        if (holiday.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_HOLIDAY_EXIST);
//        }
//    }
//
//    public Holiday holidayParameterExist(Optional<Holiday> holiday) throws VarException {
//
//        if(!holiday.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_HOLIDAY_NOT_EXIST);
//        }
//
//        return holiday.get();
//    }
//
//    public void checkNullWeekHolidayDTO(WeekHolidayDTO weekHolidayDTO) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
//        if(commonUtils.isNull(weekHolidayDTO.isMonday())){
//            varNull.add("isMonday");
//        }
//        if(commonUtils.isNull(weekHolidayDTO.isTuesday())){
//            varNull.add("isTuesday");
//        }
//        if(commonUtils.isNull(weekHolidayDTO.isWednesday())){
//            varNull.add("isWednesday");
//        }
//        if(commonUtils.isNull(weekHolidayDTO.isThursday())){
//            varNull.add("isThursday");
//        }
//        if(commonUtils.isNull(weekHolidayDTO.isFriday())){
//            varNull.add("isFriday");
//        }
//        if(commonUtils.isNull(weekHolidayDTO.isSaturday())){
//            varNull.add("isSaturday");
//        }
//        if(commonUtils.isNull(weekHolidayDTO.isSunday())){
//            varNull.add("isSunday");
//        }
//
//        throwIfAnyVariablesNull(varNull);
//    }
//
//    public void checkNullFixedHolidayDTO(FixedHolidayDTO fixedHolidayDTO) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
//        if(commonUtils.isNull(fixedHolidayDTO.getDayDate())){
//            varNull.add("Date");
//        }
//        if(commonUtils.isNull(fixedHolidayDTO.getDayMonth())){
//            varNull.add("Month");
//        }
//        if(commonUtils.isNull(fixedHolidayDTO.getDayInformation())){
//            varNull.add("Information");
//        }
//        if(commonUtils.isNull(fixedHolidayDTO.getActive())){
//            varNull.add("Is Active");
//        }
//
//        throwIfAnyVariablesNull(varNull);
//    }
//
//    public void fixedHolidayNotExist(Optional<FixedHoliday> fixedHoliday) throws VarException {
//
//        if (fixedHoliday.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_HOLIDAY_EXIST);
//        }
//    }
//
//    public FixedHoliday fixedHolidayExist(Optional<FixedHoliday> fixedHoliday) throws VarException {
//
//        if(!fixedHoliday.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_HOLIDAY_NOT_EXIST);
//        }
//
//        return fixedHoliday.get();
//    }
//
//    public void checkNullVarLimitDTO(VarLimitDTO varLimitDTO) throws VarException {
//
//        List<String> varNull = new ArrayList<>();
//
////        if(commonUtils.isNull(holidayDTO.getId())){
////            varNull.add("ID");
////        }
//        if(commonUtils.isNull(varLimitDTO.getLimitVarForex())){
//            varNull.add("Limit Var Forex");
//        }
//        if(commonUtils.isNull(varLimitDTO.getLimitVarBond())){
//            varNull.add("Limit Var Bond");
//        }
//
//        throwIfAnyVariablesNull(varNull);
//    }
//
//    public void varLimitParameterNotExist(Optional<VarLimit> varLimit) throws VarException {
//
//        if (varLimit.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_VARLIMIT_EXIST);
//        }
//    }
//
//    public VarLimit varLimitParameterExist(Optional<VarLimit> varLimit) throws VarException {
//
//        if(!varLimit.isPresent()){
//            throw new VarException(
//                    HttpStatus.BAD_REQUEST,
//                    Constants.ERR_VARLIMIT_NOT_EXIST);
//        }
//
//        return varLimit.get();
//    }
//}
