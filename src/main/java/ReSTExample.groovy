import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

class ReSTExample {

    // URL info
    String pricefxURL = "https://training.pricefx.eu/pricefx"

    //  API call info
    String pricefxReSTAPI  = "pricelistmanager.fetch"

    //  ReST API path params
    String path

    RESTClient collectRuntimeInfo() {
        /**
         def readln = javax.swing.JOptionPane.&showInputDialog

         String username = readln {'What is your partition username?'}
         String password = readln {'What is your partition password?'}
         String partition = readln {'What is your partition ( i.e. ce-0014 ) ?'}
         String pricelistId = readln {'What is the ID of the pricelist desired ?'}
         */

        def username = 'admin'
        def password = 'LKEH72F86Uc8'
        def partition = 'ce-0051'
        def pricelistId = '10041'

        username = partition+"/"+username

        // Build API call
        path = "/" +partition+ "/" +pricefxReSTAPI+ "/" +pricelistId

        println("\npricefxURL:"+pricefxURL)
        println("username:"+username)
        println("password:"+password)

        def client = new RESTClient(pricefxURL)

        client.authorization = new HTTPBasicAuthorization(username, password)

        println("\ncollectRuntimeInfo call complete\n")

        return client
    }

    /**
     * Execute call to ReST client
     */
    void runExample() {
        RESTClient client = collectRuntimeInfo()

        // Initiate ReST call
        println("Initiating ReST call using path: "+path+"\n")

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
        ReSTExample hw = new ReSTExample()
        hw.runExample()
        System.exit(0)
    }
}
