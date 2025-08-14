import { useState } from "react";
import "./App.css";

function Tree({ node }) {
  if (!node) return <span className="nil">∅</span>;
  return (
    <div className="tree-node">
      <div className="value">{node.value}</div>
      {(node.left || node.right) && (
        <div className="children">
          <Tree node={node.left} />
          <Tree node={node.right} />
        </div>
      )}
    </div>
  );
}

export default function App() {
  const [numbers, setNumbers] = useState("");
  const [result, setResult] = useState(null);
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const submit = async () => {
    setLoading(true);
    setError("");
    try {
      const res = await fetch("/api/trees", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ numbers }),
      });
      if (!res.ok) throw new Error(await res.text());
      const data = await res.json();
      setResult(data);
    } catch (e) {
      setError(e.message || "Request failed");
    } finally {
      setLoading(false);
    }
  };

  const loadPrevious = async () => {
    setError("");
    try {
      const data = await fetch("/api/trees").then((r) => r.json());
      setHistory(data);
    } catch (e) {
      setError(e.message || "Request failed");
    }
  };

  return (
    <div className="container">
      <h1>Binary Search Tree Builder</h1>

      <div className="row">
        <label>Numbers (CSV):</label>
        <input
          value={numbers}
          onChange={(e) => setNumbers(e.target.value)}
          placeholder="e.g. 4,2,8,3"
        />
        <button onClick={submit} disabled={loading || !numbers.trim()}>
          {loading ? "Submitting…" : "Submit"}
        </button>
        <button onClick={loadPrevious}>Show Previous</button>
      </div>

      {error && <div className="error">Error: {error}</div>}

      <div className="grid">
        <div>
          <h2>Result (Visualization)</h2>
          <div className="viz">
            {result?.tree ? (
              <Tree node={result.tree} />
            ) : (
              <em>No result yet</em>
            )}
          </div>
        </div>

        <div>
          <h2>Result (Raw JSON)</h2>
          <pre className="json">
            {result ? JSON.stringify(result.tree, null, 2) : "—"}
          </pre>
        </div>
      </div>

      <h2>Previous Trees</h2>
      {history.length === 0 ? (
        <em>None loaded. Click “Show Previous”.</em>
      ) : (
        <ul className="history">
          {history.map((r) => (
            <li key={r.id}>
              <div>
                <strong>ID:</strong> {r.id}
              </div>
              <div>
                <strong>Numbers:</strong> {r.numbers}
              </div>
              <details>
                <summary>Tree JSON</summary>
                <pre className="json small">
                  {JSON.stringify(r.tree, null, 2)}
                </pre>
              </details>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
