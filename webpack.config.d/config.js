var webpack = require("webpack");

// YNAB_ACCESS_TOKEN must be defined in the process environment
// (Will this be a problem for running in the browser? Will the browser's env need the secret?)

var definePlugin = new webpack.DefinePlugin(
    {
        "PROCESS_ENV": {
            "YNAB_ACCESS_TOKEN": JSON.stringify(process.env.YNAB_ACCESS_TOKEN),
        }
    }
)

config.plugins.push(definePlugin)
