package pt.utl.ist.dataProvider;import java.io.IOException;import java.util.ArrayList;import java.util.List;import javax.xml.bind.annotation.XmlAccessType;import javax.xml.bind.annotation.XmlAccessorType;import javax.xml.bind.annotation.XmlElement;import javax.xml.bind.annotation.XmlRootElement;import javax.xml.bind.annotation.XmlTransient;import org.dom4j.DocumentException;import org.dom4j.DocumentHelper;import org.dom4j.Element;import pt.utl.ist.configuration.ConfigSingleton;import com.wordnik.swagger.annotations.ApiModel;import com.wordnik.swagger.annotations.ApiModelProperty;/** * Aggregator data type * @author Emanuel * @author Simon Tzanakis (Simon.Tzanakis@theeuropeanlibrary.org) */@XmlRootElement(name = "aggregator")@XmlAccessorType(XmlAccessType.NONE)@ApiModel(value = "An Aggregator")public class Aggregator {    //    private static final Logger log = Logger.getLogger(Aggregator.class);    @XmlElement    @ApiModelProperty(value = "It's filled only by the server on a GET Request", required = false, allowableValues = "null", position = 0)    private String                    id;    @XmlElement    @ApiModelProperty(required = true, position = 1)    private String                    name;    @XmlElement    @ApiModelProperty(required = false, position = 2)    private String                    nameCode;    @XmlElement    @ApiModelProperty(required = false, position = 3)    private String                    homePage;    @XmlTransient    private List<DefaultDataProvider> dataProviders = new ArrayList<DefaultDataProvider>();    /**     * Get the Identifier of the Aggregator. The Identifier value is generated by the server side application. If     * an error occurs on the server side (e.g. "Aggregator Already Exists"), the default value (-1L) is returned.     *     * @return - Long     */    public String getId() {        return id;    }    /**     * Set the Identifier of the Aggregator. This method is used by the Server side application. A value set by the     * client side application is ignored.     *     * @param id - Long     */    public void setId(String id) {        this.id = id;    }    /**     * Get the Name of the Aggregator     *     * @return - String     */    public String getName() {        return name;    }    /**     * Set the Name of the Aggregator     *     * @param name - String     */    public void setName(String name) {        this.name = name;    }    /**     * Get the code associated to the Aggregator. The NameCode is used by the server side application     * to generate a unique file identifier for harversted metadata.     *     * @return - String     */    public String getNameCode() {        return nameCode;    }    /**     * Set the code associated to the Aggregator. The NameCode is used by the server side application     * to generate a unique file identifier for harversted metadata.     *     * @return - String     */    public void setNameCode(String nameCode) {        this.nameCode = nameCode;    }    /**     * Get the URL of the Aggregator's Home Page     *     * @return URL     */    public String getHomePage() {        return homePage;    }    //    /**    //     * Get the URL of the Aggregator's Home Page    //     *    //     * @return URL    //     */    //    public URL getHomePageUrl() {    //        try {    //            return new URL(homePage);    //        } catch (MalformedURLException e) {    //            throw new RuntimeException("Cannot create url from '" + homePage + "'!", e);    //        }    //    }    /**     * Set the URL of the Aggregator's Home Page     *     * @param homePage - URL     */    public void setHomePage(String homePage) {        this.homePage = homePage;    }    /**     * Get the List of Providers belonging to this Aggregator instance.     *     * @return -  List<DataProvider>     * @see {@link DataProvider}     */    public List<DefaultDataProvider> getDataProviders() {        return dataProviders;    }    /**     * Set the List of Providers belonging to this Aggregator instance.     *     * @param dataProviders - List<DataProvider>     * @see {@link DataProvider}     */    public void setDataProviders(List<DefaultDataProvider> dataProviders) {        this.dataProviders.addAll(dataProviders);    }    /**     * Add a Data Provider     *     * @param dataProvider - DataProvider     * @see {@link DataProvider}     */    public void addDataProvider(DefaultDataProvider dataProvider) {        dataProviders.add(dataProvider);    }    /**     * Generate an Id for Aggregator     * @param name     * @return     * @throws DocumentException     * @throws IOException     */    public static String generateId(String name) throws DocumentException, IOException {        String generatedIdPrefix = "";        for (int i = 0; (i < name.length() && i < 32); i++) {            if ((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z')) {                generatedIdPrefix += name.charAt(i);            }        }        generatedIdPrefix += "r";        return generatedIdPrefix + generateNumberSufix(generatedIdPrefix);    }    private static int generateNumberSufix(String basename) throws DocumentException, IOException {        int currentNumber = 0;        String currentFullId = basename + currentNumber;        while (((DefaultDataManager)ConfigSingleton.getRepoxContextUtil().getRepoxManager().getDataManager()).getAggregator(currentFullId) != null) {            currentNumber++;            currentFullId = basename + currentNumber;        }        return currentNumber;    }    public Aggregator() {    }    public Aggregator(String id, String name, String nameCode, String homePage, List<DefaultDataProvider> dataProviders) {        this.id = id;        this.name = name;        this.nameCode = nameCode;        this.homePage = homePage;        this.dataProviders = dataProviders;    }    /**     * Create Element from aggregator information     * @param writeDataProviders      * @return Document     */    public Element createElement(boolean writeDataProviders) {        Element aggregatorNode = DocumentHelper.createElement("aggregator");        aggregatorNode.addAttribute("id", this.getId());        aggregatorNode.addElement("name").setText(this.getName());        if (this.getNameCode() != null) {            aggregatorNode.addElement("nameCode").setText(this.getNameCode());        }        if (this.getHomePage() != null) {            aggregatorNode.addElement("url").setText(this.getHomePage().toString());        }        if (writeDataProviders && this.getDataProviders() != null) {            for (DefaultDataProvider dataProvider : this.getDataProviders()) {                aggregatorNode.add(dataProvider.createElement(true));            }        }        return aggregatorNode;    }}