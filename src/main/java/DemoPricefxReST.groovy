import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import javax.swing.JOptionPane

import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

//  see GITHUB https://github.com/rodmadden/RESTExample_Pricefx

class DemoPricefxReST {

    // URL info
    String pricefxURL = "https://training.pricefx.eu/pricefx"

    //  API call info
    String pricefxReSTAPI  = "pricelistmanager.fetch"

    //  ReST API path params
    String path

    RESTClient collectRuntimeInfo() {

        String username = JOptionPane.showInputDialog ('What is your partition username?','admin')
        String password = JOptionPane.showInputDialog ('What is your partition password?')
        String partition = JOptionPane.showInputDialog ('What is your partition ( i.e. ce-0014 ) ?','ce-0051')
        String pricelistId = JOptionPane.showInputDialog ('What is the ID of the pricelist desired ?','10041')

        username = partition+"/"+username

        // Build API call
        path = "/" +partition+ "/" +pricefxReSTAPI+ "/" +pricelistId

        def client = new RESTClient(pricefxURL)

        client.authorization = new HTTPBasicAuthorization(username, password)

        return client
    }

    /**
     * Execute call to ReST client
     */
    void runExample() {
        RESTClient client = collectRuntimeInfo()

        // Initiate ReST call
        def restResponse = client.post(path: path)
        assert restResponse.statusCode == 200

        // Pretty print results
        def jsonSlurper = new JsonSlurper()
        String json = JsonOutput.toJson(restResponse.json)
        def prettyJson = JsonOutput.prettyPrint(json)
        println(prettyJson)

        println("runExample call complete\n")
    }

    static void main(String[] args) {
        DemoPricefxReST hw = new DemoPricefxReST()
        hw.runExample()
        System.exit(0)
    }
}
